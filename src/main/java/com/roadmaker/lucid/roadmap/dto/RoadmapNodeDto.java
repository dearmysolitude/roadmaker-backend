package com.roadmaker.lucid.roadmap.dto;

import com.roadmaker.lucid.roadmap.entity.blogkeyword.BlogKeyword;
import com.roadmaker.lucid.roadmap.entity.inprogressnode.InProgressNode;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import com.roadmaker.lucid.roadmap.entity.roadmapedge.RoadmapEdge;
import com.roadmaker.lucid.roadmap.entity.roadmapnode.RoadmapNode;
import com.roadmaker.lucid.roadmap.entity.roadmapnodedata.RoadmapNodeData;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RoadmapNodeDto {
    
    private String id;
    private String type;
    private Integer width;
    private Integer height;
    private String sourcePosition;
    private String targetPosition;
    
    private Boolean done; // response 에서만 사용
    
    private String detailedContent;
    private RoadmapNodeStyleDto styleDto;
    private RoadmapNodeDataDto dataDto;
    private RoadmapNodePositionDto positionDto;
    private RoadmapNodePositionAbsoluteDto positionAbsoluteDto;
    
    private BlogKeyword blogKeyword;
    
    public RoadmapNode toEntity(Roadmap roadmap) {
        return RoadmapNode.builder()
                .roadmap(roadmap)
                .width(this.width)
                .height(this.height)
                .type(this.type)
                .sourcePosition(this.sourcePosition)
                .targetPosition(this.targetPosition)
                .detailedContent(this.detailedContent)
                .style(this.styleDto.toEntity())
                .data(this.dataDto.toEntity())
                .position(this.positionDto.toEntity())
                .positionAbsolute(this.positionAbsoluteDto.toEntity())
                .blogKeyword(this.blogKeyword)
                .build();
    }
    
    public static RoadmapNodeDto of(RoadmapNode roadmapNode) {
        return RoadmapNodeDto.builder()
                .id(roadmapNode.getClientNodeId())
                .type(roadmapNode.getType())
                .width(roadmapNode.getWidth())
                .height(roadmapNode.getHeight())
                .sourcePosition(roadmapNode.getSourcePosition())
                .targetPosition(roadmapNode.getTargetPosition())
                .detailedContent(roadmapNode.getDetailedContent())
                .done(false)
                .styleDto(RoadmapNodeStyleDto.of(roadmapNode.getStyle()))
                .dataDto(RoadmapNodeDataDto.of(roadmapNode.getData()))
                .positionDto(RoadmapNodePositionDto.of(roadmapNode.getPosition()))
                .blogKeyword(roadmapNode.getBlogKeyword())
                .build();
    }
    
    //어디 쓰는 녀석인지...?
    public static RoadmapNodeDto of(InProgressNode inProgressNode) {
        return RoadmapNodeDto.builder()
                .id(inProgressNode.getRoadmapNode().getClientNodeId())
                .type(inProgressNode.getRoadmapNode().getType())
                .width(inProgressNode.getRoadmapNode().getWidth())
                .height(inProgressNode.getRoadmapNode().getHeight())
                .sourcePosition(inProgressNode.getRoadmapNode().getSourcePosition())
                .targetPosition(inProgressNode.getRoadmapNode().getTargetPosition())
                .detailedContent(inProgressNode.getRoadmapNode().getDetailedContent())
                .done(inProgressNode.getDone())
                .styleDto(RoadmapNodeStyleDto.of(inProgressNode.getRoadmapNode().getStyle()))
                .dataDto(RoadmapNodeDataDto.of(inProgressNode.getRoadmapNode().getData()))
                .positionDto(RoadmapNodePositionDto.of(inProgressNode.getRoadmapNode().getPosition()))
                .blogKeyword(inProgressNode.getRoadmapNode().getBlogKeyword())
                .build();
    }


}
