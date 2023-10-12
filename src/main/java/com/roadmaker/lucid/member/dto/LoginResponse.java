package com.roadmaker.lucid.member.dto;

import com.roadmaker.lucid.member.entity.Member;
import lombok.*;

@Builder @ToString
@Getter @NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LoginResponse {
    private MemberResponse member;
    private TokenInfo tokenInfo;

    public static LoginResponse of(Member member, TokenInfo tokenInfo) {
        return LoginResponse.builder()
                .member(MemberResponse.of(member))
                .tokenInfo(tokenInfo)
                .build();
    }
}
