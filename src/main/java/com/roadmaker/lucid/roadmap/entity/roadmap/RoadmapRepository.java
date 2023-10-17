package com.roadmaker.lucid.roadmap.entity.roadmap;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    public Optional<Roadmap> findById(Long id);
    public List<Roadmap> findByMemberId(Long id, Sort option);

}
