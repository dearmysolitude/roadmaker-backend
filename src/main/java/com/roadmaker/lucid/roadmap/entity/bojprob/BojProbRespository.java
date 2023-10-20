package com.roadmaker.lucid.roadmap.entity.bojprob;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BojProbRespository extends JpaRepository<BojProb, Long> {
    public BojProb findByRoadmapNodeId(Long roadmapNodeId);
}
