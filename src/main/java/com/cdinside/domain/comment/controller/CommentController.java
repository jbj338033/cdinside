package com.cdinside.domain.comment.controller;

import com.cdinside.domain.comment.dto.CommentCreateRequest;
import com.cdinside.domain.comment.dto.CommentDeleteRequest;
import com.cdinside.domain.comment.dto.CommentResponse;
import com.cdinside.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/galleries/{galleryId}/posts/{postId}/comments")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "댓글 API")
public class CommentController {
    
    private final CommentService commentService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다")
    public CommentResponse createComment(
            @PathVariable Long galleryId, 
            @PathVariable Long postId, 
            @Valid @RequestBody CommentCreateRequest request) {
        return commentService.createComment(postId, request);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "댓글 목록 조회", description = "게시글의 댓글 목록을 조회합니다")
    public Page<CommentResponse> getCommentsByPost(
            @PathVariable Long galleryId,
            @PathVariable Long postId,
            @PageableDefault(size = 50) Pageable pageable) {
        return commentService.getCommentsByPost(postId, pageable);
    }
    
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "모든 댓글 조회", description = "게시글의 모든 댓글을 조회합니다 (페이징 없음)")
    public List<CommentResponse> getAllCommentsByPost(
            @PathVariable Long galleryId,
            @PathVariable Long postId) {
        return commentService.getAllCommentsByPost(postId);
    }
    
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다 (비밀번호 확인 필요)")
    public void deleteComment(
            @PathVariable Long galleryId,
            @PathVariable Long postId,
            @PathVariable Long commentId, 
            @Valid @RequestBody CommentDeleteRequest request) {
        commentService.deleteComment(commentId, request);
    }
}