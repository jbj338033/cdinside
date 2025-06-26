package com.cdinside.domain.comment.dto;

import com.cdinside.domain.comment.domain.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {
    
    private Long commentId;
    private Long postId;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static CommentResponse from(CommentEntity comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .content(comment.getContent())
                .authorName(comment.getAuthorName())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}