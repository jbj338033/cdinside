package com.cdinside.domain.comment.service;

import com.cdinside.domain.comment.domain.entity.CommentEntity;
import com.cdinside.domain.comment.domain.enums.CommentStatus;
import com.cdinside.domain.comment.domain.repository.CommentRepository;
import com.cdinside.domain.comment.dto.CommentCreateRequest;
import com.cdinside.domain.comment.dto.CommentDeleteRequest;
import com.cdinside.domain.comment.dto.CommentResponse;
import com.cdinside.domain.post.domain.entity.PostEntity;
import com.cdinside.domain.post.domain.enums.PostStatus;
import com.cdinside.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    
    @Transactional
    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        PostEntity post = postRepository.findByPostIdAndStatus(postId, PostStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
        
        CommentEntity comment = CommentEntity.builder()
                .post(post)
                .content(request.getContent())
                .authorName(request.getAuthorName())
                .password(request.getPassword())
                .build();
        
        CommentEntity savedComment = commentRepository.save(comment);
        post.incrementCommentCount();
        
        return CommentResponse.from(savedComment);
    }
    
    public Page<CommentResponse> getCommentsByPost(Long postId, Pageable pageable) {
        return commentRepository.findByPostPostIdAndStatusOrderByCreatedAtAsc(postId, CommentStatus.ACTIVE, pageable)
                .map(CommentResponse::from);
    }
    
    public List<CommentResponse> getAllCommentsByPost(Long postId) {
        return commentRepository.findByPostPostIdAndStatusOrderByCreatedAtAsc(postId, CommentStatus.ACTIVE)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
    
    @Transactional
    public void deleteComment(Long commentId, CommentDeleteRequest request) {
        CommentEntity comment = commentRepository.findByCommentIdAndStatus(commentId, CommentStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다"));
        
        if (!comment.verifyPassword(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        
        comment.delete();
        comment.getPost().decrementCommentCount();
    }
}