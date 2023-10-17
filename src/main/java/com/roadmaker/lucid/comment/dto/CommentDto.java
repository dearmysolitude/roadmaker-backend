package com.roadmaker.lucid.comment.dto;

import com.roadmaker.lucid.comment.entity.Comment;
import com.roadmaker.lucid.common.BaseTimeEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    @NotBlank
    Long roadmapId;
    @NotBlank
    String content;
    @NotBlank
    String nickname;
    String createAt;
    String updatedAt;

    private static String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. dd");
        return dateTime.format(formatter);
    }

    public static CommentDto of(Comment comment) {
        return CommentDto.builder()
                .createAt(formatDate(comment.getCreatedAt()))
                .updatedAt(formatDate(comment.getUpdatedAt()))
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .roadmapId(comment.getRoadmap().getId())
                .build();
    }
}