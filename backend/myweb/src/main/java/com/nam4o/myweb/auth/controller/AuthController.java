package com.nam4o.myweb.auth.controller;

import com.nam4o.myweb.auth.TokenProvider;
import com.nam4o.myweb.auth.dto.TokenResDto;
import com.nam4o.myweb.auth.entity.Token;
import com.nam4o.myweb.auth.repository.TokenRepository;
import com.nam4o.myweb.common.BaseResponseBody;
import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;
    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    @PostMapping("/refresh")
    public ResponseEntity<? extends BaseResponseBody> regenerateAccessToken(@RequestBody HttpServletRequest request,
                                                                            HttpServletResponse response,
                                                                            String refreshToken) {

        Token token = tokenRepository.findByRefreshToken(refreshToken).orElse(null);
        System.out.println("1");
        if(token != null) {
            tokenProvider.checkRefreshTokenAndReIssueAccessToken(request, response, refreshToken);
            TokenResDto tokenResDto = TokenResDto.builder()
                    .grantType("Bearer")
                    .accessToken(token.getAccessToken())
                    .refreshToken(token.getRefreshToken())
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, tokenResDto));
        }
        System.out.println("2");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseBody.error(ErrorCode.NOT_VALID_REQUEST.getErrorCode(),ErrorCode.NOT_VALID_REQUEST.getMessage()));

    }
}
