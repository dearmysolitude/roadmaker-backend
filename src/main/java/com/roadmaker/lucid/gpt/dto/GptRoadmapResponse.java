package com.roadmaker.lucid.gpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class GptRoadmapResponse {
    private List<RoadmapData> roadmapData;
    private List<NodeDetail> nodeDetail;
}
