package com.nam4o.myweb.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberProfileResDto {

    private String name;

    private String email;

    private String nickname;

    private String gender;

    private String address;

}
