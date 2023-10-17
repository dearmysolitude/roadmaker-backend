package com.roadmaker.lucid.roadmap.service;

import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public interface RoadmapService {
    public Long createRoadmap(CreateRoadmapRequest createRoadmapRequest, Member member);
    // public UploadImageResponse uploadThumbnail(Roadmap roadmap, MultipartFile image) throws IOException;
    public Roadmap findRoadmapById(Long roadmapId);
    public RoadmapFindResponse findByPage(Integer page, Integer size, String flag);
}
