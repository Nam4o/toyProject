package com.nam4o.myweb.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nam4o.myweb.domain.member.repository.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    @Column(name = "name", length = 8, nullable = false)
    private String name;

    @Email
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "nickname", length = 8, nullable = false, unique = true)
    private String nickname;

    @Column(name = "gender", nullable = false)
    private int gender;

    @Column(name = "profile_image", length = 255, nullable = true, unique = true)
    private String profileImage;

    @Column(name = "phone", length = 13, nullable = false, unique = true)
    private String phone;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "is_active")
    private Boolean isActive;


    @ManyToMany
    @JoinTable(
            name = "member_authorities",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authorities> authorities;
}
