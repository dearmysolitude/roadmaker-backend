package com.roadmaker.lucid.roadmap.entity.roadmapnode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roadmaker.lucid.common.BaseTimeEntity;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import com.roadmaker.lucid.roadmap.entity.roadmapnodedata.RoadmapNodeData;
import com.roadmaker.lucid.roadmap.entity.roadmapnodeposition.RoadmapNodePosition;
import com.roadmaker.lucid.roadmap.entity.roadmapnodepositionabsolute.RoadmapNodePositionAbsolute;
import com.roadmaker.lucid.roadmap.entity.roadmapnodestyle.RoadmapNodeStyle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "ROADMAP_NODE")
public class RoadmapNode extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROADMAP_ID")
    @JsonBackReference
    private Roadmap roadmap;
    private String clientNodeId;
    private Integer width;
    private Integer height;
    private String targetPosition;
    private String sourcePosition;
    private String type;

    @Column(columnDefinition = "TEXT")
    private String detailedContent;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROADMAP_NODE_STYLE_ID")
    private RoadmapNodeStyle style;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROADMAP_NODE_DATA_ID")
    private RoadmapNodeData roadmapNodeData;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROADMAP_NODE_POSITION_ID")
    private RoadmapNodePosition roadmapNodePosition;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROADMAP_NODE_POSITION_ABSOLUTE_ID")
    private RoadmapNodePositionAbsolute positionAbsolute;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="BLOG_KEYWORD")
    private BlogKeyword blogKeyword;
    public String getKeywordFromBlogKeyword() {return blogKeyword.getKeyword();}

}
