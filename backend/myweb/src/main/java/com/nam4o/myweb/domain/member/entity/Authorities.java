package com.nam4o.myweb.domain.member.entity;

import com.nam4o.myweb.domain.member.repository.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;


    @Column(name = "authority_name")
    private String authorityName;

    @ManyToOne
    private Member member;
}
