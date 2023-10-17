package com.roadmaker.lucid.like.entity;

import com.roadmaker.lucid.common.BaseTimeEntity;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ROADMAP_LIKE")
public class Like extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROADMAP_ID")
    private Roadmap roadmap;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Like(Roadmap roadmap, Member member) {
        this.roadmap = roadmap;
        this.member = member;
    }
}
