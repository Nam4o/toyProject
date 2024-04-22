package com.nam4o.myweb.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenResDto {

    private String grantType;

    private String accessToken;

    private String refreshToken;

}
