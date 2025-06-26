package com.cdinside.domain.recommendation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecommendationResponse {
    
    private Long postId;
    private Long recommendCount;
    private Long unrecommendCount;
    private String message;
    
    public static RecommendationResponse of(Long postId, Long recommendCount, Long unrecommendCount, String message) {
        return RecommendationResponse.builder()
                .postId(postId)
                .recommendCount(recommendCount)
                .unrecommendCount(unrecommendCount)
                .message(message)
                .build();
    }
}