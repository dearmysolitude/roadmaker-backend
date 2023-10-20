package com.roadmaker.lucid.roadmap.dto;

import com.roadmaker.lucid.roadmap.entity.roadmapnodepositionabsolute.RoadmapNodePositionAbsolute;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoadmapNodePositionAbsoluteDto {
    private Integer x;
    private Integer y;
    
    public RoadmapNodePositionAbsolute toEntity() {
        return RoadmapNodePositionAbsolute.builder()
                .x(this.x)
                .y(this.y)
                .build();
    }
    public static RoadmapNodePositionAbsoluteDto of(RoadmapNodePositionAbsolute roadmapNodePositionAbsolute) {
        return RoadmapNodePositionAbsoluteDto.builder()
                .x(roadmapNodePositionAbsolute.getX())
                .y(roadmapNodePositionAbsolute.getY())
                .build();
    }
}
