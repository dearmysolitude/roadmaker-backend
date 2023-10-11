package com.roadmaker.lucid.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
