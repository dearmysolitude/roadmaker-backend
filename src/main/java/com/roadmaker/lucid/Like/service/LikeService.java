package com.roadmaker.lucid.like.service;

public interface LikeService {
    public boolean isLiked(Long roadmapId, Long memberId);
    public int getLikeCount(Long roadmapId);
    public void likRoadmap(Long roadmapId, Long memberId);
    public void unlikeRoadmap(Long roadmapId, Long memberId);
}
