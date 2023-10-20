package com.roadmaker.lucid.comment.service;

import com.roadmaker.lucid.comment.dto.CommentDto;
import com.roadmaker.lucid.comment.dto.CommentResponse;
import com.roadmaker.lucid.member.entity.Member;

public interface CommentService {
    public CommentResponse findByRoadmapId(Long roadmapId, Integer page, Integer size);
    public CommentResponse findByMemberId(Long memberId, Integer page, Integer size);
    public boolean saveComment(CommentDto commentDto, Member member);
}
