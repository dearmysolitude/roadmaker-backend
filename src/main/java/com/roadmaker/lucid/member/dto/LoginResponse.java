package com.roadmaker.lucid.member.dto;

import com.roadmaker.lucid.member.entity.Member;
import lombok.Builder;

@Builder
public class LoginResponse {
    private MemberResponse member;
    private TokenInfo tokenInfo;
}
