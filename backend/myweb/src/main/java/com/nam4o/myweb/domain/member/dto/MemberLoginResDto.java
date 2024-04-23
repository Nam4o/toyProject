package com.nam4o.myweb.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberLoginResDto {

    private String grantType;

    private String accessToken;

    private String refreshToken;
}
