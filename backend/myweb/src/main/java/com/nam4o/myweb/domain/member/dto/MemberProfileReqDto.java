package com.nam4o.myweb.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberProfileReqDto {

    private String address;

    private String nickname;

    private String profileImage;

}
