package com.roadmaker.lucid.member.dto;

import com.roadmaker.lucid.member.entity.Member;
import lombok.*;

@Builder @Getter
@ToString @NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String email;
    private String nickname;
    private String bio;
    private String avatarUrl;
    private String githubUrl;
    private String blogUrl;
    private String baekjoonId;
//    private Integer level;
//    private Integer exp;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .bio(member.getBio())
                .avatarUrl(member.getAvatarUrl())
                .githubUrl(member.getGithubUrl())
                .blogUrl(member.getBlogUrl())
                .baekjoonId(member.getBaekjoonId())
//                .level(member.getLevel())
//                .exp(member.getExp())
                .build();
    }
}
