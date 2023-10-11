package com.roadmaker.lucid.member.Controller;

import com.roadmaker.lucid.member.dto.*;
import com.roadmaker.lucid.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController @RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController  {
    private final MemberService memberService;
    public final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        // 이메일 중복 검사
        if (memberService.isDuplicatedEmail(signupRequest.getEmail()) && memberService.isDuplicatedNickname(signupRequest.getNickname())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("이메일과 닉네임 중복");
        }
        else if (memberService.isDuplicatedEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이메일 중복");
        }
        // 닉네임 중복 검사
        else if(memberService.isDuplicatedNickname(signupRequest.getNickname())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("닉네임 중복");
        }

        memberService.signUp(signupRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        String mail = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        TokenInfo tokenInfo = memberService.login(mail, password);
        MemberResponse member = memberService.findMemberByMail(mail); // 주의

        LoginResponse loginResponse = LoginResponse.builder()
                .member(member)
                .tokenInfo(tokenInfo)
                .build();

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
