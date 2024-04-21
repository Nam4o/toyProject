package com.nam4o.myweb.domain.member.service;

import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import com.nam4o.myweb.domain.member.dto.MemberSignupReqDto;
import com.nam4o.myweb.domain.member.entity.Member;
import com.nam4o.myweb.domain.member.repository.MemberRepository;
import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Getter
@RequiredArgsConstructor
public class MemberSignService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long memberSignup(MemberSignupReqDto request) {
//        try {
            if(memberRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new Exceptions(ErrorCode.EMAIL_EXIST);
            }

            Member member = Member.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .nickname(request.getNickname())
                    .gender(request.getGender())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .isActive(true)
                    .build();

            return memberRepository.save(member).getId();
//        } catch (ValidationException e) {
//            throw new ValidationException(e);
//        }

    }

}
