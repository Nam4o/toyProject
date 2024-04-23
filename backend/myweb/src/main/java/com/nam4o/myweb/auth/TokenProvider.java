package com.nam4o.myweb.auth;

import com.nam4o.myweb.auth.entity.Token;
import com.nam4o.myweb.auth.repository.TokenRepository;
import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import com.nam4o.myweb.domain.member.entity.Member;
import com.nam4o.myweb.domain.member.repository.MemberRepository;
import com.nam4o.myweb.util.RedisUtility;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisUtility redisUtility;
    private final TokenRepository tokenRepository;
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final Long tokenValidityInMiliseconds;
    private Key key;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    public TokenProvider(
            AuthenticationManagerBuilder authenticationManagerBuilder, MemberRepository memberRepository, @Value("${jwt.secret}") String secret, StringRedisTemplate stringRedisTemplate, RedisUtility redisUtility, TokenRepository tokenRepository,
            @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInseconds) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.memberRepository = memberRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisUtility = redisUtility;
        this.tokenRepository = tokenRepository;
        this.secret = secret;
        this.tokenValidityInMiliseconds = tokenValidityInseconds * 1000;
        byte[] keyBytes = Decoders.BASE64URL.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date accessTokenValidity = new Date(now + tokenValidityInMiliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
//                .claim("token_type", "access")
                .setIssuedAt(new Date())
                .setExpiration(accessTokenValidity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String email) {
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims();

        long now = (new Date()).getTime();

        Date refreshTokenValidity = new Date(now + tokenValidityInMiliseconds * 21);

        return Jwts.builder()
                .setSubject(email)
//                .claim(AUTHORITIES_KEY, authorities)
                .setClaims(claims)
//                .claim("token_type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(refreshTokenValidity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
//        tokenRepository.save(new Token(authentication.getName(), refreshToken));
//        redisUtility.setDataExpire(authentication.getName(), refreshToken, refreshTokenValidity.getTime());
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

//    public boolean validateToken(String accessToken) {
//        try {
//            TokenProvider.isExpired(accessToken, secret);
//
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
//            if(!stringRedisTemplate.hasKey(accessToken)) {
//                return false;
//            }
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            log.info("Invalid Jwt Token", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired Jwt Token", e);
//            Token foundTokenInfo = tokenRepository.findByAccessToken(accessToken).orElseThrow();
//            // exception 추가
//            String refreshToken = foundTokenInfo.getRefreshToken();
//
//            TokenProvider.isExpired(refreshToken, secret);
//
//            String email = foundTokenInfo.getId();
//
//            Member member = memberRepository
//                    .findByEmail(email).orElseThrow(() -> new Exceptions(ErrorCode.MEMBER_NOT_EXIST));
//
//            String newAccessToken = createAccessToken(authenticationManagerBuilder.getObject().authenticate(getAuthentication(accessToken)));
//
//            updateTokenRepo(email, refreshToken, newAccessToken);
//
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported Jwt Token", e);
//        } catch (IllegalArgumentException e) {
//            log.info("Jwt claims string is empty", e);
//        }
//
//        return false;
//    }
    public void updateTokenRepo(String email, String refreshToken, String accessToken) {
        tokenRepository.save(new Token(email, refreshToken, accessToken));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader(accessHeader))
                .map(accessToken -> accessToken.substring(7));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader(refreshHeader))
                .map(refreshToken -> refreshToken.substring(7));
    }


    // 토큰에서 사용자 정보 추출
    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);

        return claims.getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("this token is inValid. {}", e.getMessage());
            return false;
        }
    }

}
