package com.roadmaker.lucid.member.dto;

import com.roadmaker.lucid.member.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
public class SignupRequest {

    @NotNull
    String email;

    @NotNull
    String password;

    @NotNull
    String nickname;

    public Member toEntity(PasswordEncoder passwordEncoder) {

        return Member.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(passwordEncoder.encode(this.password))
                .level(1)
                .exp(0)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
