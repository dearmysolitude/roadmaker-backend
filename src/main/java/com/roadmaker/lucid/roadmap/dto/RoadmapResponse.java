package com.roadmaker.lucid.roadmap.dto;

import com.roadmaker.lucid.inProgressRoadmap.entity.InProgressRoadmap;
import com.roadmaker.lucid.member.dto.MemberResponse;
import com.roadmaker.lucid.roadmap.entity.inprogressnode.InProgressNode;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RoadmapResponse {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private Boolean isJoined;
    private Boolean isLiked;
    private Integer joinCount;
    private Integer likeCount;
    private MemberResponse member;
    private String createdAt;
    private String updatedAt;

    private RoadmapViewportDto viewport;
    private List<RoadmapEdgeDto> edges;
    private List<RoadmapNodeDto> nodes;
    
    private static String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. dd");
        return dateTime.format(formatter);
    }
    
    //로드맵을 보여주는 데이터
    private static RoadmapResponse buildFromRoadmap(Roadmap roadmap, List<RoadmapNodeDto> nodes, boolean isJoined, boolean isLiked) {
        return RoadmapResponse.builder()
                .id(roadmap.getId())
                .title(roadmap.getTitle())
                .description(roadmap.getDescription())
                .thumbnailUrl(roadmap.getThumbnailUrl())
                .isJoined(isJoined)
                .isLiked(isLiked)
                .joinCount(roadmap.getInProgressRoadmapCount())
                .likeCount(roadmap.getLikeCount())
                .member(MemberResponse.of(roadmap.getMember()))
                .createdAt(formatDate(roadmap.getCreatedAt()))
                .updatedAt(formatDate(roadmap.getUpdatedAt()))
                .nodes(nodes)
                .edges(roadmap.getRoadmapEdges().stream().map(RoadmapEdgeDto::of).toList())
                .viewport(RoadmapViewportDto.of(roadmap.getRoadmapViewport()))
                .build();
    }
    
    // 로그인 안 하거나, 로드맵에 참여하지 않은 사람에 보게 되는 화면에 필요한 로드맵 정보
    public static RoadmapResponse of(Roadmap roadmap, Boolean isLiked) {
        return buildFromRoadmap(roadmap, null, false, isLiked);
    }
    //로드맵에 참가한 사람의 dto
    public static RoadmapResponse of(Roadmap roadmap, Boolean isLiked, List<InProgressNode> nodes) {
        return buildFromRoadmap(roadmap, nodes.stream().map(RoadmapNodeDto::of).toList(), true, isLiked);
    }
}
