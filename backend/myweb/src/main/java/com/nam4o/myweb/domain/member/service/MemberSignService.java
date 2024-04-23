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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class MemberSignService {
    private final Logger logger = LoggerFactory.getLogger(MemberSignService.class);

    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

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
                .password(passwordEncoder.encode(request.getPassword()))
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
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            Member member = memberRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new Exceptions(ErrorCode.MEMBER_NOT_EXIST));
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            String accessToken = tokenProvider.createAccessToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(accessToken);

            tokenProvider.updateTokenRepo(request.getEmail(), refreshToken, accessToken);
            return MemberLoginResDto.builder()
                    .grantType("Bearer")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthorizationServiceException e) {
            log.info("auth error", e);
            return null;
        }

    }
}
