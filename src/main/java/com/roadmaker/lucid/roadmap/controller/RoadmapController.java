package com.roadmaker.lucid.roadmap.controller;

import com.roadmaker.lucid.comment.dto.CommentResponse;
import com.roadmaker.lucid.comment.service.CommentService;
import com.roadmaker.lucid.common.annotation.LoginMember;
import com.roadmaker.lucid.common.annotation.LoginRequired;
import com.roadmaker.lucid.inProgressRoadmap.entity.InProgressRoadmap;
import com.roadmaker.lucid.inProgressRoadmap.entity.InProgressRoadmapRepository;
import com.roadmaker.lucid.like.service.LikeService;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.member.service.MemberService;
import com.roadmaker.lucid.roadmap.dto.CreateRoadmapRequest;
import com.roadmaker.lucid.roadmap.dto.NodeStatusChangeDto;
import com.roadmaker.lucid.roadmap.dto.RoadmapFindResponse;
import com.roadmaker.lucid.roadmap.dto.RoadmapResponse;
import com.roadmaker.lucid.roadmap.entity.inprogressnode.InProgressNode;
import com.roadmaker.lucid.roadmap.entity.inprogressnode.InProgressNodeRepository;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import com.roadmaker.lucid.roadmap.service.RoadmapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController @Slf4j @Validated
@RequiredArgsConstructor
@RequestMapping("/api/roadmaps")
public class RoadmapController {
    private final MemberService memberService;
    private final RoadmapService roadmapService;
    private final InProgressNodeRepository inProgressNodeRepository;
    private final InProgressRoadmapRepository inProgressRoadmapRepository;
    private final CommentService commentService;
    private final LikeService likeService;

    @LoginRequired
    @PostMapping
    public ResponseEntity<Long> createRoadmap(@Valid @RequestBody CreateRoadmapRequest createRoadmapRequest,
                                              @LoginMember Member member) {
        Long roadmapId = roadmapService.createRoadmap(createRoadmapRequest, member);
        return new ResponseEntity<>(roadmapId, HttpStatus.CREATED);
    }
    
//    @LoginRequired
//    @PostMapping("/{roadmapId}/thumbnails")
//    public ResponseEntity<>
    @GetMapping
    public ResponseEntity<RoadmapFindResponse> getRoadmaps(@RequestParam(name = "page")Integer page, @RequestParam(name="order-type") String ordertype) {
        int size = 8;
        
        RoadmapFindResponse roadmaps = roadmapService.findByPage(page, size, ordertype);
        return new ResponseEntity<>(roadmaps, HttpStatus.OK);
    }
    
    //인증 여부에 관계 없이 사용할 수 있는 로드맵을 부르는 메서드
    @GetMapping("/{roadmapId}")
    public ResponseEntity<RoadmapResponse> getRoadmap(@PathVariable Long roadmapId) {
        
        //현재 사용자가 로그인 한 멤버인지 찾아본다
        Optional<Member> memberOptional = memberService.getLoggedInMember();
        Roadmap roadmap = roadmapService.findByRoadmapId(roadmapId);
        Boolean isLiked = false;
        
        RoadmapResponse roadmapResponse;
        
        //멤버 로그인이 되어 있지 않은 경우
        if(memberOptional.isEmpty()) {
            roadmapResponse = RoadmapResponse.of(roadmap, isLiked);
            return new ResponseEntity<>(roadmapResponse, HttpStatus.OK);
        }

        //이 멤버가 이 로드맵을 진행하고 있는지 판단: InProgressRoadmap에 있는지 판단
        Optional<InProgressRoadmap> inProgressRoadmap = inProgressRoadmapRepository.findByRoadmapIdAndMemberId(roadmapId, memberOptional.get().getId());
        isLiked = likeService.isLiked(roadmapId, memberOptional.get().getId());
        
        //로드맵을 진행하고 있지 않다면
        if(inProgressRoadmap.isEmpty()) {
            roadmapResponse = RoadmapResponse.of(roadmap, isLiked);
            return new ResponseEntity<>(roadmapResponse, HttpStatus.OK);
        }
        
        //로드맵을 진행하고 있다면: 노드 진행 상황을 가져온다
        List<InProgressNode> inProgressNodes = inProgressNodeRepository.findByRoadmapIdAndMemberId(roadmapId, memberOptional.get().getId());
        roadmapResponse = RoadmapResponse.of(roadmap, isLiked, inProgressNodes);
        
        return new ResponseEntity<>(roadmapResponse, HttpStatus.OK);
    }
    
    @GetMapping(path = "/load-roadmap/{roadmapId}/comments")
    public ResponseEntity<CommentResponse> loadRoadmapComment(@PathVariable Long roadmapId, @RequestParam Integer page) {
        int size = 8;
        return new ResponseEntity<>(commentService.findByRoadmapId(roadmapId, page, size), HttpStatus.OK);
    }
    
    @LoginRequired
    @PostMapping(path = "/{roadmapId}/join")
    public ResponseEntity<Integer> joinRoadmap(@PathVariable Long roadmapId, @LoginMember Member member) {
        Roadmap roadmap = roadmapService.findByRoadmapId(roadmapId);
        Integer count = roadmapService.joinRoadmap(roadmap, member);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @LoginRequired
    @PatchMapping("/in-progress-node/{inPorgressNodeId}/done")
    public ResponseEntity<HttpStatus> nodeDone(@PathVariable Long inProgressNodeId, @LoginMember Member member) {
        Optional<InProgressNode> inProgressNodeOptional = inProgressNodeRepository.findById(inProgressNodeId);
        InProgressNode inProgressNode = inProgressNodeOptional.orElse(null);
        
        if(inProgressNode == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        Long joiningMemberId = Objects.requireNonNull(inProgressNode).getMember().getId();
        
        if(!joiningMemberId.equals(member.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        NodeStatusChangeDto nodeStatusChangeDto = NodeStatusChangeDto.builder()
                .inProgressNodeId(inProgressNodeId)
                .done(inProgressNode.getDone())
                .build();
        if(!roadmapService.changeNodeStatus(nodeStatusChangeDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
//    @GetMapping
//    public ResponseEntity<RoadmapFindResponse> 
}
