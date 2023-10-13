package com.roadmaker.lucid.roadmap.entity.roadmap;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roadmaker.lucid.Like.entity.Like;
import com.roadmaker.lucid.common.BaseTimeEntity;
import com.roadmaker.lucid.inProgressRoadmap.entity.InProgressRoadmap;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.roadmap.entity.roadmapedge.RoadmapEdge;
import com.roadmaker.lucid.roadmap.entity.roadmapnode.RoadmapNode;
import com.roadmaker.lucid.roadmap.entity.roadmapviewport.RoadmapViewport;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ROADMAP")
public class Roadmap extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private String thumbnailUrl;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @OneToMany(mappedBy = "roadmap")
    private List<Like> likes;
    @OneToMany(mappedBy = "roadmap")
    private List<InProgressRoadmap> inProgressRoadmaps;
    @OneToMany(mappedBy = "roadmap")
    @JsonManagedReference
    private List<RoadmapNode> roadmapNodes;
    @OneToMany(mappedBy = "roadmap")
    @JsonManagedReference
    private List<RoadmapEdge> roadmapEdges;
    @OneToOne
    @JoinColumn(name = "ROADMAP_VIEWPORT_ID")
    @Setter
    private RoadmapViewport roadmapViewport;

    public int getLikeCount() {return likes.size(); }

    public int getInProgressRoadmapCount() {return inProgressRoadmaps.size(); }

    @Builder
    public Roadmap(String title, String description, String thumbnailUrl, Member member) {
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.member = member;
    }
}
