package com.roadmaker.lucid.roadmap.controller;

import com.roadmaker.lucid.comment.service.CommentService;
import com.roadmaker.lucid.common.annotation.LoginMember;
import com.roadmaker.lucid.common.annotation.LoginRequired;
import com.roadmaker.lucid.inProgressRoadmap.entity.InProgressRoadmapRepository;
import com.roadmaker.lucid.like.service.LikeService;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.member.service.MemberService;
import com.roadmaker.lucid.roadmap.entity.inprogressnode.InProgressNodeRepository;
import com.roadmaker.lucid.roadmap.service.RoadmapServiceImple;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @Slf4j @Validated
@RequiredArgsConstructor
@RequestMapping("/api/roadmaps")
public class RoadmapController {
    private final MemberService memberService;
    private final RoadmapServiceImple roadmapServiceImple;
    private final InProgressNodeRepository inProgressNodeRepository;
    private final InProgressRoadmapRepository inProgressRoadmapRepository;
    private final CommentService commentService;
    private final LikeService likeService;

//    @LoginRequired
//    @PostMapping
//    public ResponseEntity<Long> createRoadmap(@Valid @RequestBody CreateRoadmapRequest createRoadmapRequest,
//                                              @LoginMember Member member) {
////        Long roadmapId = roadmapServiceImple.create
//    }
}
