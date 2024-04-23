package com.nam4o.myweb.domain.member.controller;

import com.nam4o.myweb.common.BaseResponseBody;
import com.nam4o.myweb.domain.member.dto.MemberLoginReqDto;
import com.nam4o.myweb.domain.member.dto.MemberLoginResDto;
import com.nam4o.myweb.domain.member.dto.MemberSignupReqDto;
import com.nam4o.myweb.domain.member.service.MemberSignService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Getter
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSignService memberSignService;

    @PostMapping("/signup")
    public ResponseEntity<? extends BaseResponseBody> signup(@RequestBody @Valid MemberSignupReqDto request) throws Exception {
        Long memberId = memberSignService.memberSignup(request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, memberId));
    }

    @PostMapping("/login")
    public ResponseEntity<? extends BaseResponseBody> login(@RequestBody @Valid MemberLoginReqDto request) throws Exception {
        MemberLoginResDto response = memberSignService.memberLogin(request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, response));
    }

}
