package com.roadmaker.lucid.inProgressRoadmap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roadmaker.lucid.common.BaseTimeEntity;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "IN_PROGRESS_ROADMAP")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InProgressRoadmap  extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="ROADMAP_ID")
    @JsonBackReference
    private Roadmap roadmap;


}
