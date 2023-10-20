package com.roadmaker.lucid.roadmap.entity.bojprob;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Table(name = "BOJ_PROB")
public class BojProb {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long roadmapNodeId;
    @Column(unique = true)
    private String bojNum;
    @Column(unique = true)
    private String bojTitle;
    
    @Builder
    public BojProb(Long roadmapNodeId, String bojNum, String bojTitle) {
        this.bojNum = bojNum;
        this.bojTitle = bojTitle;
        this.roadmapNodeId = roadmapNodeId;
    }
}
