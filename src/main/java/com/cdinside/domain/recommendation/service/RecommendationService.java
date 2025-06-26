package com.cdinside.domain.recommendation.service;

import com.cdinside.domain.post.domain.entity.PostEntity;
import com.cdinside.domain.post.domain.enums.PostStatus;
import com.cdinside.domain.post.domain.repository.PostRepository;
import com.cdinside.domain.recommendation.domain.entity.RecommendationEntity;
import com.cdinside.domain.recommendation.domain.enums.RecommendationType;
import com.cdinside.domain.recommendation.domain.repository.RecommendationRepository;
import com.cdinside.domain.recommendation.dto.RecommendationRequest;
import com.cdinside.domain.recommendation.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationService {
    
    private final RecommendationRepository recommendationRepository;
    private final PostRepository postRepository;
    
    @Transactional
    public RecommendationResponse recommendPost(Long postId, RecommendationRequest request, String ipAddress) {
        PostEntity post = postRepository.findByPostIdAndStatus(postId, PostStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
        
        Optional<RecommendationEntity> existingRecommendation = 
                recommendationRepository.findByPostPostIdAndIpAddress(postId, ipAddress);
        
        String message;
        
        if (existingRecommendation.isPresent()) {
            RecommendationEntity recommendation = existingRecommendation.get();
            RecommendationType currentType = recommendation.getRecommendationType();
            RecommendationType newType = request.getRecommendationType();
            
            if (currentType == newType) {
                throw new RuntimeException("이미 " + (newType == RecommendationType.RECOMMEND ? "추천" : "비추천") + "하셨습니다");
            }
            
            if (currentType == RecommendationType.RECOMMEND) {
                post.decrementRecommendCount();
            } else {
                post.decrementUnrecommendCount();
            }
            
            if (newType == RecommendationType.RECOMMEND) {
                post.incrementRecommendCount();
                message = "추천하였습니다";
            } else {
                post.incrementUnrecommendCount();
                message = "비추천하였습니다";
            }
            
            recommendation.updateRecommendationType(newType);
            
        } else {
            RecommendationEntity newRecommendation = RecommendationEntity.builder()
                    .post(post)
                    .ipAddress(ipAddress)
                    .recommendationType(request.getRecommendationType())
                    .build();
            
            recommendationRepository.save(newRecommendation);
            
            if (request.getRecommendationType() == RecommendationType.RECOMMEND) {
                post.incrementRecommendCount();
                message = "추천하였습니다";
            } else {
                post.incrementUnrecommendCount();
                message = "비추천하였습니다";
            }
        }
        
        return RecommendationResponse.of(
                postId,
                (long) post.getRecommendCount(),
                (long) post.getUnrecommendCount(),
                message
        );
    }
    
    public RecommendationResponse getRecommendationStatus(Long postId) {
        PostEntity post = postRepository.findByPostIdAndStatus(postId, PostStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
        
        long recommendCount = recommendationRepository.countByPostIdAndRecommendationType(postId, RecommendationType.RECOMMEND);
        long unrecommendCount = recommendationRepository.countByPostIdAndRecommendationType(postId, RecommendationType.UNRECOMMEND);
        
        return RecommendationResponse.of(
                postId,
                recommendCount,
                unrecommendCount,
                "추천 현황 조회 완료"
        );
    }
}