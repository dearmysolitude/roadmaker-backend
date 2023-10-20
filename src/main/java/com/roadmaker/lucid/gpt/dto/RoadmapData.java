package com.roadmaker.lucid.gpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @NoArgsConstructor(access = AccessLevel.PRIVATE) @Setter
public class RoadmapData {
    
    @NotBlank
    private String id;
    
    @NotBlank
    @JsonInclude
    private String content;
    
    public RoadmapData(@NotBlank String id, @NotBlank String content) {
        this.id = id;
        this.content = content;
    }
    
}
