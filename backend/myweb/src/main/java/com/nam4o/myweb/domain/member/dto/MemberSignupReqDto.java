package com.nam4o.myweb.domain.member.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class MemberSignupReqDto {
    @NotBlank(message = "이름을 입력해 주세요.")
    @Length(min = 2, max = 8)
    private String name;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Length(max = 50)
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Length(min = 8, max = 16)
    private String password;

    @NotBlank(message = "닉네임을 입력해 주세요.")
    @Length(min = 3, max = 8)
    private String nickname;

    @NotNull(message = "성별을 입력해 주세요.")
    @Min(0)
    @Max(1)
    private int gender;

    @NotBlank(message = "휴대폰 번호를 입력해 주세요.")
    @Length(min = 13, max = 13)
    private String phone;

    @NotBlank(message = "주소를 입력해 주세요.")
    @Length(max = 255)
    private String address;

    @Getter @Setter
    public static class nickname {
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일을 입력해 주세요.")
        @Length(max = 50)
        private String email;

        @NotBlank(message = "닉네임을 입력해 주세요.")
        @Length(min = 3, max = 8)
        private String nickname;
    }
}
