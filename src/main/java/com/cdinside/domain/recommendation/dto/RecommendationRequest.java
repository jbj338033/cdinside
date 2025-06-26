package com.cdinside.domain.recommendation.dto;

import com.cdinside.domain.recommendation.domain.enums.RecommendationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendationRequest {
    
    @NotNull(message = "추천 타입은 필수입니다")
    private RecommendationType recommendationType;
    
    public RecommendationRequest(RecommendationType recommendationType) {
        this.recommendationType = recommendationType;
    }
}