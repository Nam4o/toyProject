package com.nam4o.myweb.domain.member.service;

import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import com.nam4o.myweb.domain.member.dto.MemberProfileReqDto;
import com.nam4o.myweb.domain.member.dto.MemberProfileResDto;
import com.nam4o.myweb.domain.member.entity.Member;
import com.nam4o.myweb.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  MemberInfoService {

    private final MemberRepository memberRepository;

    public MemberProfileResDto myProfile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new Exceptions(ErrorCode.MEMBER_NOT_EXIST));

        return MemberProfileResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .gender(member.getGender() == 0?"femail":"mail")
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();
    }

    public MemberProfileResDto memberProfile(String memeberNickname) {
        Member member = memberRepository.findByEmail(memeberNickname)
                .orElseThrow(() -> new Exceptions(ErrorCode.MEMBER_NOT_EXIST));

        return MemberProfileResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .gender(member.getGender() == 0?"femail":"mail")
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();
    }

    public MemberProfileResDto updateMemberProfile(MemberProfileReqDto request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new Exceptions(ErrorCode.MEMBER_NOT_EXIST));
        member.update(request);
        memberRepository.save(member);

        return MemberProfileResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .gender(member.getGender() == 0?"femail":"mail")
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();
    }
}
