package com.roadmaker.lucid.inProgressRoadmap.entity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InProgressRoadmapRepository extends JpaRepository<InProgressRoadmap, Long> {
    public List<InProgressRoadmap> findByMemberId(Long memberId, Sort option);

    public Optional<InProgressRoadmap> findByRoadmapIdAndMemberId(Long roadmapId, Long memberId);
    
    public Integer countByRoadmapId(Long roadmapId);
}
