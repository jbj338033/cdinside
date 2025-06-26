package com.cdinside.domain.post.controller;

import com.cdinside.domain.post.dto.PostResponse;
import com.cdinside.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Post Search", description = "게시글 검색 API")
public class PostSearchController {
    
    private final PostService postService;
    
    @GetMapping("/hot")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "인기글 목록 조회", description = "추천수 기준 인기글을 조회합니다")
    public Page<PostResponse> getHotPosts(
            @RequestParam(defaultValue = "5") @Parameter(description = "최소 추천수") Integer minRecommendCount,
            @PageableDefault(size = 20) Pageable pageable) {
        return postService.getHotPosts(minRecommendCount, pageable);
    }
    
    @GetMapping("/trending")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "트렌딩 게시글 조회", description = "추천수 기준으로 게시글을 정렬하여 조회합니다")
    public Page<PostResponse> getTrendingPosts(@PageableDefault(size = 20) Pageable pageable) {
        return postService.getPostsSortedByRecommend(pageable);
    }
    
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "인기순 게시글 조회", description = "조회수 기준으로 게시글을 정렬하여 조회합니다")
    public Page<PostResponse> getPopularPosts(@PageableDefault(size = 20) Pageable pageable) {
        return postService.getPostsSortedByViewCount(pageable);
    }
}