package com.nam4o.myweb.domain.member.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class MemberLoginReqDto {
    @Email
    @Length(max = 50)
    private String email;

    @Length(min = 8, max = 16)
    private String password;
}
