package com.cdinside.domain.recommendation.domain.repository;

import com.cdinside.domain.recommendation.domain.entity.RecommendationEntity;
import com.cdinside.domain.recommendation.domain.enums.RecommendationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {
    
    Optional<RecommendationEntity> findByPostPostIdAndIpAddress(Long postId, String ipAddress);
    
    @Query("SELECT COUNT(r) FROM RecommendationEntity r WHERE r.post.postId = :postId AND r.recommendationType = :type")
    long countByPostIdAndRecommendationType(@Param("postId") Long postId, @Param("type") RecommendationType type);
    
    boolean existsByPostPostIdAndIpAddress(Long postId, String ipAddress);
}