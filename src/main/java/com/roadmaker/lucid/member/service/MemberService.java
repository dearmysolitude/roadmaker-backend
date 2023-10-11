package com.roadmaker.lucid.member.service;

import com.roadmaker.lucid.member.authentication.JwtProvider;
import com.roadmaker.lucid.member.dto.MemberResponse;
import com.roadmaker.lucid.member.dto.SignupRequest;
import com.roadmaker.lucid.member.dto.TokenInfo;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.member.entity.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;
    @Transactional
    public void signUp(SignupRequest signupRequest) {
        Member member = signupRequest.toEntity(passwordEncoder);
        memberRepository.save(member);
    }

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtProvider.generateToken(authentication);
    }

    public MemberResponse findMemberByMail(String email) {

        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) return null;

        return MemberResponse.of(member);
    }
    public MemberResponse findMemberByNickname(String nickname) {
        Member member  = memberRepository.findByNickname(nickname).orElse(null);
        if (member == null) return null;
        return MemberResponse.of(member);
    }

    public boolean isDuplicatedEmail(String email) {
        return findMemberByMail(email) != null;
    }
    public boolean isDuplicatedNickname(String nickname) { return findMemberByNickname(nickname) != null; }

    public Optional<Member> getLoggedInMember() {
        String token = jwtProvider.resolveToken(httpServletRequest);

        // 토큰이 비어 있거나
        if(token == null || jwtProvider.validateToken(token)) {
            return Optional.empty();
        }

        Authentication authentication = jwtProvider.getAuthentication(token);
        String email = authentication.getName();

        return memberRepository.findByEmail(email);
    }
}
