package com.roadmaker.lucid.inProgressRoadmap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roadmaker.lucid.common.BaseTimeEntity;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.roadmap.entity.inprogressnode.InProgressNode;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "IN_PROGRESS_ROADMAP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InProgressRoadmap  extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="ROADMAP_ID")
    @JsonBackReference
    private Roadmap roadmap;
    
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;
    
    @OneToMany(mappedBy = "inProgressRoadmap")
    @JsonBackReference
    private List<InProgressNode> inProgressNodes;
    
    private Boolean done;

    @Builder
    public InProgressRoadmap(Roadmap roadmap, Member member, List<InProgressNode> inProgressNodes, Boolean done) {
        this.roadmap = roadmap;
        this.member = member;
        this.inProgressNodes = inProgressNodes;
        this.done = done;
    }

}
