package com.roadmaker.lucid.roadmap.service;

import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.roadmap.dto.CreateRoadmapRequest;
import com.roadmaker.lucid.roadmap.dto.NodeStatusChangeDto;
import com.roadmaker.lucid.roadmap.dto.RoadmapDto;
import com.roadmaker.lucid.roadmap.dto.RoadmapFindResponse;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public interface RoadmapService {
    public Long createRoadmap(CreateRoadmapRequest createRoadmapRequest, Member member);
    // public UploadImageResponse uploadThumbnail(Roadmap roadmap, MultipartFile image) throws IOException;
    public Roadmap findByRoadmapId(Long roadmapId);
    public RoadmapFindResponse findByPage(Integer page, Integer size, String flag);
    public List<RoadmapDto> findJoinedByMemberId(Long memberId);
    public List<RoadmapDto> findCreatedByMemberId(Long memberId);
    public Integer joinRoadmap(Roadmap roadmap, Member member);
    public boolean changeNodeStatus(NodeStatusChangeDto nodeStatusChangeDto);
//    public RoadmapFindResponse findByKeyword(String keyword, Integer size, Integer page);
}
