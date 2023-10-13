package com.roadmaker.lucid.inProgressRoadmap.entity;

import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InProgressRoadmapRepository extends JpaRepository<Roadmap, Long> {
    public Roadmap findRoadmapById(Long id);
    public List<Roadmap> findByMemberId(Long memberId, Sort option);

}
