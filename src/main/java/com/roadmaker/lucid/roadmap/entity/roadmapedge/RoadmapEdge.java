package com.roadmaker.lucid.roadmap.entity.roadmapedge;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roadmaker.lucid.common.BaseTimeEntity;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ROADMAP_EDGE")
public class RoadmapEdge extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROADMAP_ID")
    @JsonBackReference
    private Roadmap roadmap;

    private String clientEdgeId;
    private String source;
    private String target;
    private String type;
    private Boolean animated;

    @Builder
    public RoadmapEdge(Roadmap roadmap, String clientEdgeId, String source, String target, String type,
                       Boolean animated) {
        this.animated = animated;
        this.clientEdgeId = clientEdgeId;
        this.roadmap = roadmap;
        this.source = source;
        this.target = target;
        this.type = type;
    }
}
