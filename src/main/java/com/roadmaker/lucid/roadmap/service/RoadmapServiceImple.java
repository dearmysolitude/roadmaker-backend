package com.roadmaker.lucid.roadmap.service;

import com.roadmaker.lucid.common.exception.ConflictException;
import com.roadmaker.lucid.common.exception.NotFoundException;
import com.roadmaker.lucid.inProgressRoadmap.entity.InProgressRoadmap;
import com.roadmaker.lucid.inProgressRoadmap.entity.InProgressRoadmapRepository;
import com.roadmaker.lucid.member.entity.Member;
import com.roadmaker.lucid.member.entity.MemberRepository;
import com.roadmaker.lucid.roadmap.dto.*;
import com.roadmaker.lucid.roadmap.entity.inprogressnode.InProgressNode;
import com.roadmaker.lucid.roadmap.entity.inprogressnode.InProgressNodeRepository;
import com.roadmaker.lucid.roadmap.entity.roadmap.Roadmap;
import com.roadmaker.lucid.roadmap.entity.roadmap.RoadmapRepository;
import com.roadmaker.lucid.roadmap.entity.roadmapedge.RoadmapEdge;
import com.roadmaker.lucid.roadmap.entity.roadmapedge.RoadmapEdgeRepository;
import com.roadmaker.lucid.roadmap.entity.roadmapnode.RoadmapNode;
import com.roadmaker.lucid.roadmap.entity.roadmapnode.RoadmapNodeRepository;
import com.roadmaker.lucid.roadmap.entity.roadmapviewport.RoadmapViewport;
import com.roadmaker.lucid.roadmap.entity.roadmapviewport.RoadmapViewportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoadmapServiceImple implements RoadmapService{
    
    private final RoadmapRepository roadmapRepository;
    private final RoadmapNodeRepository roadmapNodeRepository;
    private final RoadmapEdgeRepository roadmapEdgeRepository;
    private final RoadmapViewportRepository roadmapViewportRepository;
    private final InProgressRoadmapRepository inProgressRoadmapRepository;
    private final InProgressNodeRepository inProgressNodeRepository;
    private final MemberRepository memberRepository;
//    private final ImageService imageService;
    
    
    @Override
    public Long createRoadmap(CreateRoadmapRequest createRoadmapRequest, Member member) {
        //Viewport 저장
        RoadmapViewport roadmapViewport = createRoadmapRequest.getViewport().toEntity();
        roadmapViewportRepository.save(roadmapViewport);
        
        //로드맵 저장
        Roadmap roadmap = createRoadmapRequest.getRoadmap().toEntity(member);
        roadmapRepository.save(roadmap);
        
        //edge 저장
        List<CreateRoadmapRequest.RoadmapEdgeDto> roadmapEdgeDtos = createRoadmapRequest.getEdges();
        List<RoadmapEdge> roadmapEdges = roadmapEdgeDtos.stream()
                .map(edgeDto -> edgeDto.toEntity(roadmap)).toList();
        roadmapEdgeRepository.saveAll(roadmapEdges);
        
        //node 저장
        List<CreateRoadmapRequest.RoadmapNodeDto> roadmapNodeDtos = createRoadmapRequest.getNodes();
        List<RoadmapNode> roadmapNodes = roadmapNodeDtos.stream()
                .map(nodeDto -> nodeDto.toEntity(roadmap)).toList();
        roadmapNodeRepository.saveAll(roadmapNodes);
        
        return roadmap.getId();
    }
    
    // public UploadImageResponse uploadThumbnail(Roadmap roadmap, MultipartFile image) throws IOException;
    @Override
    public Roadmap findByRoadmapId(Long roadmapId) {
        return roadmapRepository.findById(roadmapId).orElseThrow(NotFoundException::new);
    }
    
    public RoadmapFindResponse findByPage(Integer page, Integer size, String flag) {
        Page<Roadmap> roadmaps;
        PageRequest pageRequest;
        
        if (Objects.equals(flag, "recent")) {
            pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "CreatedAt"));
            roadmaps = roadmapRepository.findAll(pageRequest);

            List<RoadmapDto> roadmapDtoList = roadmaps.stream().map(roadmap -> RoadmapDto.of(roadmap, roadmap.getMember())).toList();

            return RoadmapFindResponse.builder()
                    .totalPage((long) roadmaps.getTotalPages())
                    .result(roadmapDtoList)
                    .build();
        } else if (Objects.equals(flag, "most-liked")) {
//            pageRequest = PageRequest.of(page - 1, size);
//            return roadmapRepository.;
            pageRequest = PageRequest.of(page-1, size);
            roadmaps = roadmapRepository.findAllOrderByLikesCountDesc(pageRequest);
            List<RoadmapDto> roadmapDtoList = roadmaps.stream().map(roadmap -> RoadmapDto.of(roadmap, roadmap.getMember())).toList();
            return RoadmapFindResponse.builder()
                    .totalPage((long) roadmaps.getTotalPages())
                    .result(roadmapDtoList)
                    .build();
        } else {
            throw new NotFoundException("Order Type 이 정의되지 않음");
        }
    }
        
    public List<RoadmapDto> findJoinedByMemberId(Long memberId) {
        List<InProgressRoadmap> inProgressRoadmaps = inProgressRoadmapRepository.findByMemberId(memberId, Sort.by(Sort.Direction.DESC, "CreatedAt"));
        List<RoadmapDto> roadmapDtos = new ArrayList<>();
        
        if(inProgressRoadmaps.isEmpty()) {
            RoadmapDto roadmapDto = RoadmapDto.builder()
                    .build();
            roadmapDtos.add(roadmapDto);
            return roadmapDtos;
        }
        
        inProgressRoadmaps.forEach(inProgressRoadmap -> {
            Roadmap roadmap = roadmapRepository.findById(inProgressRoadmap.getRoadmap().getId()).orElse(null);
            RoadmapDto roadmapDto = RoadmapDto.of(Objects.requireNonNull(roadmap), roadmap.getMember());
            if(roadmapDto != null) {
                roadmapDtos.add(roadmapDto);
            }
        });
        
        return roadmapDtos;
    }
    public List<RoadmapDto> findCreatedByMemberId(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        List<Roadmap> roadmaps = roadmapRepository.findByMemberId(memberId, Sort.by(Sort.Direction.DESC, "CreatedAt"));
        return roadmaps.stream().map(roadmap -> RoadmapDto.of(roadmap, member.get())).toList();
    }
    public Integer joinRoadmap(Roadmap roadmap, Member member) {
        Optional<InProgressRoadmap> inProgressRoadmapOptional = inProgressRoadmapRepository.findByRoadmapIdAndMemberId(roadmap.getId(), member.getId());
        if(inProgressRoadmapOptional.isPresent()) {
            throw new ConflictException("Already Joined this roadmap");
        }
        
        InProgressRoadmap inProgressRoadmap = InProgressRoadmap.builder()
                .roadmap(roadmap)
                .member(member)
                .done(false)
                .build();
        
        inProgressRoadmapRepository.save(inProgressRoadmap);
        
        List<RoadmapNode> roadmapNodes = roadmapNodeRepository.findByRoadmapId(roadmap.getId());
        
        roadmapNodes
                .forEach(node -> {
                    InProgressNode inProgressNode = InProgressNode.builder()
                            .roadmap(roadmap)
                            .roadmapNode(node)
                            .member(member)
                            .inProgressRoadmap(inProgressRoadmap)
                            .done(false)
                            .build();
                    inProgressNodeRepository.save(inProgressNode);
                });
        return inProgressRoadmapRepository.countByRoadmapId(roadmap.getId());
    }
    @Override
    public boolean changeNodeStatus(NodeStatusChangeDto nodeStatusChangeDto) {
        
        Long nodeId = nodeStatusChangeDto.getInProgressNodeId();
        Optional<InProgressNode> inProgressNodeOptional = inProgressNodeRepository.findById(nodeId);
        InProgressNode inProgressNode = inProgressNodeOptional.orElse(null);
        if (inProgressNode== null) {
            return false;
        }
        
        inProgressNode.setDone(!Boolean.TRUE.equals(nodeStatusChangeDto.getDone()));
        inProgressNodeRepository.save(inProgressNode);
        
        return true;
    }
//    public RoadmapFindResponse findByKeyword(String keyword, Integer size, Integer page) {
//        PageRequest pageRequest = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "CreatedAt"));
//        return roadmapRepository.findBySearchOption(pageRequest, keyword);
//    }


}
