package com.cdinside.domain.recommendation.controller;

import com.cdinside.domain.recommendation.dto.RecommendationRequest;
import com.cdinside.domain.recommendation.dto.RecommendationResponse;
import com.cdinside.domain.recommendation.service.RecommendationService;
import com.cdinside.global.common.utils.IpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/galleries/{galleryId}/posts/{postId}/recommendations")
@RequiredArgsConstructor
@Tag(name = "Recommendation", description = "추천/비추천 API")
public class RecommendationController {
    
    private final RecommendationService recommendationService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시글 추천/비추천", description = "게시글을 추천하거나 비추천합니다 (IP 기반 중복 방지)")
    public RecommendationResponse recommendPost(
            @PathVariable Long galleryId,
            @PathVariable Long postId,
            @Valid @RequestBody RecommendationRequest request,
            HttpServletRequest httpRequest) {
        String ipAddress = IpUtils.getClientIpAddress(httpRequest);
        return recommendationService.recommendPost(postId, request, ipAddress);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "추천 현황 조회", description = "게시글의 추천/비추천 현황을 조회합니다")
    public RecommendationResponse getRecommendationStatus(
            @PathVariable Long galleryId,
            @PathVariable Long postId) {
        return recommendationService.getRecommendationStatus(postId);
    }
}