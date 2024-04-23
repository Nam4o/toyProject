package com.nam4o.myweb.domain.member.service;

import com.nam4o.myweb.auth.TokenProvider;
import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import com.nam4o.myweb.domain.member.dto.MemberLoginReqDto;
import com.nam4o.myweb.domain.member.dto.MemberLoginResDto;
import com.nam4o.myweb.domain.member.dto.MemberSignupReqDto;
import com.nam4o.myweb.domain.member.entity.Authorities;
import com.nam4o.myweb.domain.member.entity.Member;
import com.nam4o.myweb.domain.member.repository.AuthoritiesRepository;
import com.nam4o.myweb.domain.member.repository.MemberRepository;
import com.nam4o.myweb.domain.member.repository.Role;
import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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
    private final AuthoritiesRepository authoritiesRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private  final TokenProvider tokenProvider;
    @Transactional
    public Long memberSignup(MemberSignupReqDto request) {
        if(memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new Exceptions(ErrorCode.EMAIL_EXIST);
        }

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
//                .authorities(Collections.singleton())
                .build();


        Long memberId = memberRepository.save(member).getId();

        Authorities authority = Authorities.builder()
                .member(member)
                .authorityName(Role.USER.getRole().toString())
                .build();

        authoritiesRepository.save(authority);

        member.addRole(authority);

        return memberId;
    }

    public MemberLoginResDto memberLogin(MemberLoginReqDto request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), "");
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exceptions(ErrorCode.MEMBER_NOT_EXIST));

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String accessToken = tokenProvider.createAccessToken(authentication);

        return MemberLoginResDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(tokenProvider.createRefreshToken(accessToken))
                .build();
    }
}
