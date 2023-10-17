package com.roadmaker.lucid.like.controller;

import com.roadmaker.lucid.common.annotation.LoginMember;
import com.roadmaker.lucid.common.annotation.LoginRequired;
import com.roadmaker.lucid.like.dto.LikeRoadmapResponse;
import com.roadmaker.lucid.like.service.LikeService;
import com.roadmaker.lucid.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;
    @LoginRequired
    @PostMapping("/like-roadmap/{roadmapId}")
    public LikeRoadmapResponse likeRoadmap(@PathVariable Long roadmapId, @LoginMember Member member) {
        Long memberId = member.getId();

        if(likeService.isLiked(roadmapId, memberId)) {
            likeService.unlikeRoadmap(roadmapId, memberId);
        } else {
            likeService.likRoadmap(roadmapId, memberId);
        }

        return LikeRoadmapResponse
                .from(likeService.isLiked(roadmapId, memberId),
                        likeService.getLikeCount(roadmapId));
    }

    @GetMapping("/roadmap/{roadmapId}/likeCount")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Long roadmapId) {
        Integer likeCount = likeService.getLikeCount(roadmapId);
        return ResponseEntity.ok(likeCount);
    }
}
