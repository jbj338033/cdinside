package com.cdinside.domain.comment.domain.repository;

import com.cdinside.domain.comment.domain.entity.CommentEntity;
import com.cdinside.domain.comment.domain.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    
    Page<CommentEntity> findByPostPostIdAndStatusOrderByCreatedAtAsc(Long postId, CommentStatus status, Pageable pageable);
    
    List<CommentEntity> findByPostPostIdAndStatusOrderByCreatedAtAsc(Long postId, CommentStatus status);
    
    Optional<CommentEntity> findByCommentIdAndStatus(Long commentId, CommentStatus status);
    
    long countByPostPostIdAndStatus(Long postId, CommentStatus status);
}