package com.cdinside.domain.post.domain.repository;

import com.cdinside.domain.post.domain.entity.PostEntity;
import com.cdinside.domain.post.domain.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    
    Page<PostEntity> findByGalleryGalleryIdAndStatusOrderByCreatedAtDesc(Long galleryId, PostStatus status, Pageable pageable);
    
    Optional<PostEntity> findByPostIdAndStatus(Long postId, PostStatus status);
    
    @Query("SELECT p FROM PostEntity p WHERE p.gallery.galleryId = :galleryId AND p.status = :status AND (p.title LIKE %:keyword% OR p.content LIKE %:keyword%) ORDER BY p.createdAt DESC")
    Page<PostEntity> findByGalleryIdAndStatusAndKeyword(@Param("galleryId") Long galleryId, @Param("status") PostStatus status, @Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT p FROM PostEntity p WHERE p.status = :status AND p.recommendCount >= :minRecommendCount ORDER BY p.recommendCount DESC")
    Page<PostEntity> findHotPosts(@Param("status") PostStatus status, @Param("minRecommendCount") Integer minRecommendCount, Pageable pageable);
    
    @Query("SELECT p FROM PostEntity p WHERE p.status = :status ORDER BY p.recommendCount DESC")
    Page<PostEntity> findByStatusOrderByRecommendCountDesc(@Param("status") PostStatus status, Pageable pageable);
    
    @Query("SELECT p FROM PostEntity p WHERE p.status = :status ORDER BY p.viewCount DESC")
    Page<PostEntity> findByStatusOrderByViewCountDesc(@Param("status") PostStatus status, Pageable pageable);
    
    long countByGalleryGalleryIdAndStatus(Long galleryId, PostStatus status);
    
    Page<PostEntity> findByGalleryGalleryIdAndStatusOrderByRecommendCountDesc(Long galleryId, PostStatus status, Pageable pageable);
    
    Page<PostEntity> findByGalleryGalleryIdAndStatusOrderByViewCountDesc(Long galleryId, PostStatus status, Pageable pageable);
}