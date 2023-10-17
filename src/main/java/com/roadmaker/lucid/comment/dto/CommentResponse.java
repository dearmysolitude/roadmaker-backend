package com.roadmaker.lucid.comment.dto;

import com.roadmaker.lucid.comment.entity.Comment;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CommentResponse {
    Long totalPage;
//    String next;
//    String previous;
    List<CommentDto> result;
}
