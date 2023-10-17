package com.roadmaker.lucid.like.service;

import com.roadmaker.lucid.like.entity.Like;
import com.roadmaker.lucid.like.entity.LikeRepository;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.member.entity.MemberRepository;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import com.roadmaker.lucid.roadmap.entity.roadmap.RoadmapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final RoadmapRepository roadmapRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean isLiked(Long roadmapId, Long memberId) {
        return likeRepository.existByRoadmapIdAndMemberId(roadmapId, memberId);
    }
    @Transactional
    public int getLikeCount(Long roadmapId) {
        return likeRepository.countByRoadmapId(roadmapId);
    }
    @Transactional
    public void likRoadmap(Long roadmapId, Long memberId) {
        Roadmap roadmap = roadmapRepository.findById(roadmapId).orElseThrow(null);
        Member member = memberRepository.findById(memberId).orElseThrow(null);
        Like like = new Like(roadmap, member);
        likeRepository.save(like);
    }
    public void unlikeRoadmap(Long roadmapId, Long memberId) {
        likeRepository.deleteByRoadmapIdAndMemberId(roadmapId, memberId);
    }
}
