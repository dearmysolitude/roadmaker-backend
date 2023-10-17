package com.roadmaker.lucid.comment.service;

import com.roadmaker.lucid.comment.dto.CommentDto;
import com.roadmaker.lucid.comment.dto.CommentResponse;
import com.roadmaker.lucid.comment.entity.Comment;
import com.roadmaker.lucid.comment.entity.CommentRepository;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.member.entity.MemberRepository;
import com.roadmaker.lucid.roadmap.entity.roadmap.RoadmapRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

//pagenation은 다시 구현해야함

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final RoadmapRepository roadmapRepository;
    private final MemberRepository memberRepository;

    public CommentResponse findByRoadmapId(Long roadmapId, Integer page, Integer size) {

        if(roadmapRepository.findById(roadmapId).orElse(null) == null) {
            throw new EntityNotFoundException();
        }

        int pageMod = page - 1;

        //기본적인 페이지네이션으로만 구현
        PageRequest pageRequest = PageRequest.of(pageMod, size, Sort.by(Sort.Direction.DESC, "CreatedAt"));
        Page<CommentDto> comments = commentRepository.findyByMemberId(roadmapId, pageRequest).map(CommentDto::of);

        //pagenation 은 다시 구현해야함

        return CommentResponse.builder()
                .totalPage((long)comments.getTotalPages())
                .result(comments.getContent())
                .build();
    }

    public CommentResponse findByMemberId(Long memberId, Integer page, Integer size) {

        if(roadmapRepository.findById(memberId).orElse(null) == null) {
            throw new EntityNotFoundException();
        }

        int pageMod = page - 1;

        //기본적인 페이지네이션으로만 구현
        PageRequest pageRequest = PageRequest.of(pageMod, size, Sort.by(Sort.Direction.DESC, "CreatedAt"));
        Page<CommentDto> comments = commentRepository.findyByMemberId(memberId, pageRequest).map(CommentDto::of);

        return CommentResponse.builder()
                .totalPage((long)comments.getTotalPages())
                .result(comments.getContent())
                .build();
    }

    public boolean saveComment(CommentDto commentDto, Member member) {
        Comment comment = Comment.builder()
                .roadmap(roadmapRepository.findById(commentDto.getRoadmapId()).orElseThrow(null))
                .content(commentDto.getContent())
                .member(member)
                .build();
        if (comment.getRoadmap() == null || comment.getMember() == null) { return false; }

        commentRepository.save(comment);
        return true;
    }
}
