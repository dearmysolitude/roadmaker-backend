package com.roadmaker.lucid.comment.controller;

import com.roadmaker.lucid.comment.dto.CommentDto;
import com.roadmaker.lucid.comment.service.CommentService;
import com.roadmaker.lucid.common.annotation.LoginMember;
import com.roadmaker.lucid.common.annotation.LoginRequired;
import com.roadmaker.lucid.member.entity.Member;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @LoginRequired
    @PostMapping("/save-comment")
    public ResponseEntity<HttpStatus> saveComment(@LoginMember Member member, @RequestBody CommentDto commentRequest) {
        if(! commentService.saveComment(commentRequest, member)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
