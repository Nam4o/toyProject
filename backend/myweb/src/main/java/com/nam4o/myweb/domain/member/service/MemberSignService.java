package com.nam4o.myweb.domain.member.service;

import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import com.nam4o.myweb.domain.member.dto.MemberSignupReqDto;
import com.nam4o.myweb.domain.member.entity.Authorities;
import com.nam4o.myweb.domain.member.entity.Member;
import com.nam4o.myweb.domain.member.repository.MemberRepository;
import com.nam4o.myweb.domain.member.repository.Role;
import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@Getter
@RequiredArgsConstructor
public class MemberSignService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long memberSignup(MemberSignupReqDto request) {
        if(memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new Exceptions(ErrorCode.EMAIL_EXIST);
        }
        Authorities authority = Authorities.builder()
                .authorityName(Role.USER.getRole().toString())
                .build();

//        Set<Authorities> auth = new HashSet<>();
//        auth.add(Role.USER);

        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .gender(request.getGender())
                .phone(request.getPhone())
                .address(request.getAddress())
                .isActive(true)
                .authorities(Collections.singleton(authority))
                .build();

        return memberRepository.save(member).getId();

    }

}
