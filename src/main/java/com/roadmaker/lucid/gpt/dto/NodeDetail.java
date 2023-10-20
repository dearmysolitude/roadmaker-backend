package com.roadmaker.lucid.gpt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDetail {
    private String id;
    private String detailedContent;
    
    public NodeDetail(String content) { this.detailedContent = content;}
    public NodeDetail(String id, String detailedContent) {
        this.detailedContent = detailedContent;
        this.id = id;
    }
}
