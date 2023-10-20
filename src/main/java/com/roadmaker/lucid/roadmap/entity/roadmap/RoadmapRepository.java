package com.roadmaker.lucid.roadmap.entity.roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    public Optional<Roadmap> findById(Long roadmapid);
    public List<Roadmap> findByMemberId(Long memberId, Sort option);
    
    @Query(value = "SELECT e FROM Roadmap e JOIN e.likes l GROUP BY e ORDER BY COUNT (l) DESC")
    public Page<Roadmap> findAllOrderByLikesCountDesc(Pageable pageable);

}
