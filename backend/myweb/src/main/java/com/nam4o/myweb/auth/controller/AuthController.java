package com.nam4o.myweb.auth.controller;

import com.nam4o.myweb.auth.TokenProvider;
import com.nam4o.myweb.auth.repository.TokenRepository;
import com.nam4o.myweb.common.BaseResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

//    @PostMapping("/refresh")
//    public ResponseEntity<? extends BaseResponseBody> regenerateAccessToken(HttpServletRequest request) {
//        String accessToken = tokenProvider.extractAccessToken(request).orElse(null);
//        String refreshToken = tokenProvider.extractRefreshToken(request).orElse(null);
//        if(refreshToken != null) {
//            tokenRepository.
//        }
//
//    }
}
