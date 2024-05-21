package com.nam4o.myweb.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nam4o.myweb.domain.article.entity.Article;
import com.nam4o.myweb.domain.member.dto.MemberProfileReqDto;
import com.nam4o.myweb.domain.member.repository.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.*;

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

    
    @OneToMany(mappedBy = "member")
    private Set<Authorities> authorities;

    @OneToMany(mappedBy = "member")
    private List<Article> articles;

    public void addRole(Authorities authority){
        if(this.authorities == null) {
            this.authorities = new HashSet<>();
            this.authorities.add(authority);
        } else {
            this.authorities.add(authority);
        }

    }

    public void update(MemberProfileReqDto request) {
        if(request.getNickname() != null) {
            this.nickname = request.getNickname();
        }
        if(request.getAddress() != null) {
            this.address = request.getAddress();
        }
        if(request.getProfileImage() != null) {
            this.profileImage = request.getProfileImage();
        }
    }
}
