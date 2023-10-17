package com.roadmaker.lucid.roadmap.entity.inprogressnode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InProgressNodeRepository extends JpaRepository<InProgressNode, Long> {
    public List<InProgressNode> findByRoadmapIdAndMemberId(Long roadmapId, Long memberId);
}
