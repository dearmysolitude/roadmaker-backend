package com.roadmaker.lucid.member.service;

import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(()-> new UsernameNotFoundException("해당 유저를 찾을 수 없음"));
    }

    //해당하는 User의 데이터가 존재한다면 UserDetails객체로 만들어 리턴
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .authorities(member.getAuthorities().toArray(new GrantedAuthority[0]))
                .build();
    }
}
