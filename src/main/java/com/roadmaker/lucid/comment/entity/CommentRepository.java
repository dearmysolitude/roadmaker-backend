package com.roadmaker.lucid.comment.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Page<Comment> findByRoadmapId(Long roadmapId, PageRequest pageRequest);
    public List<Comment> findByRoadmapId(Long roadmapId);

    public Page<Comment> findyByMemberId(Long memberId, PageRequest pageRequest);
    public List<Comment> findyByMemberId(Long memberId);

}
