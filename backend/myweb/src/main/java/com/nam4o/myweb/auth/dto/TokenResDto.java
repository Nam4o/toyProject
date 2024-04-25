package com.nam4o.myweb.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TokenResDto {

    private String grantType;

    private String accessToken;

    private String refreshToken;

}
