package com.nam4o.myweb.auth;

import com.nam4o.myweb.auth.dto.TokenResDto;
import com.nam4o.myweb.auth.repository.TokenRepository;
import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import com.nam4o.myweb.common.exception.JwtAuthenticationEntryPoint;
import com.nam4o.myweb.domain.member.entity.Member;
import com.nam4o.myweb.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;
    private GrantedAuthoritiesMapper grantedAuthoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isAllowedPath(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }
            String accessToken = tokenProvider.extractAccessToken(request).orElse(null);
//            System.out.println("============================");
//            System.out.println(accessToken);
//            System.out.println("============================");
            String refreshToken = tokenProvider.extractRefreshToken(request)
//                    .filter(tokenProvider::isTokenValid)
                    .orElse(null);
//            System.out.println("============================");
//            System.out.println(refreshToken);
//            System.out.println("============================");
//            System.out.println(TokenProvider.isExpired(accessToken));

            if(tokenRepository.findByAccessToken(accessToken).isEmpty()) {
                sendUnauthorizedResponse(response, "AccessToken is Invalid");
                return;
            }
            if (accessToken != null && tokenProvider.isTokenValid(accessToken)) {
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }

        } catch (ExpiredJwtException e) {
            sendUnauthorizedResponse(response, "401");
        }
        catch (Exception e) {
            SecurityContextHolder.clearContext();
            logger.error("Authentication error: ", e);
            sendUnauthorizedResponse(response, "Authentication error: " + e.getMessage());
        }
    }



    private boolean isAllowedPath(String requestUri) {
        List<String> allowedPaths = Arrays.asList("/api/member/login", "/api/member/signup", "/api/member/nickname", "/swagger-ui/", "/v3/", "/api/refresh");
        return allowedPaths.stream().anyMatch(path -> requestUri.startsWith(path));
    }


    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken, String newAccessToken) {
        tokenRepository.findByRefreshToken(refreshToken)
                        .ifPresent(token -> {
                            String newRefreshToken = updateRefreshToken(token.getAccessToken());
//                            tokenProvider.updateTokenRepo(token.getId(), newR efreshToken, tokenProvider.createAccessToken(authentication)
                            System.out.println("save new Token to TokenRepository ( Redis )");
                            tokenProvider.updateTokenRepo(token.getId(), newRefreshToken, newAccessToken);
                            System.out.println(tokenRepository.findById(token.getId()));
                            response.setHeader(accessHeader, newAccessToken);
                            response.setHeader(refreshHeader, newRefreshToken);
                        });

    }


    private String updateRefreshToken(String accessToken) {
        String newRefreshToken = tokenProvider.
                createRefreshToken(tokenProvider.extractSubject(accessToken));
        tokenProvider.updateTokenRepo(tokenProvider.extractSubject(accessToken), newRefreshToken, accessToken);
        return newRefreshToken;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

}
