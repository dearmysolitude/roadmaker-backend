package com.roadmaker.lucid.like.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    public boolean existsByRoadmapIdAndMemberId(Long roadmapId, Long memberId);
    public int countByRoadmapId(Long roadmapId);
    public void deleteByRoadmapIdAndMemberId(Long roadmapId, Long memberId);
}
