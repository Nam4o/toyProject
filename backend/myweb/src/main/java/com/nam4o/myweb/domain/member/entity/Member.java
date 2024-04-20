package com.nam4o.myweb.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", length = 8)
    private String name;

    @Email
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "nickname", length = 8)
    private String nickname;

    @Column(name = "gender")
    private int gender;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "phone", length = 13)
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;
}
