package com.nam4o.myweb.domain.member.service;

import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import com.nam4o.myweb.domain.member.entity.Member;
import com.nam4o.myweb.domain.member.entity.UserDetailsImpl;
import com.nam4o.myweb.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new Exceptions(ErrorCode.MEMBER_NOT_EXIST));
        if(member != null) {
            UserDetailsImpl userDetails = new UserDetailsImpl(member);
            return userDetails;
        }

        return null;
    }
}
