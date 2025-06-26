package com.cdinside.domain.post.controller;

import com.cdinside.domain.post.dto.*;
import com.cdinside.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/galleries/{galleryId}/posts")
@RequiredArgsConstructor
@Tag(name = "Post", description = "게시글 API")
public class PostController {
    
    private final PostService postService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "게시글 작성", description = "특정 갤러리에 게시글을 작성합니다")
    public PostResponse createPost(@PathVariable Long galleryId, @Valid @RequestBody PostCreateRequest request) {
        return postService.createPost(galleryId, request);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "갤러리별 게시글 목록 조회", description = "특정 갤러리의 게시글 목록을 조회합니다")
    public Page<PostResponse> getPostsByGallery(
            @PathVariable Long galleryId,
            @RequestParam(required = false) @Parameter(description = "검색 키워드") String keyword,
            @RequestParam(required = false) @Parameter(description = "정렬 기준 (latest, recommend, view)") String sort,
            @PageableDefault(size = 20) Pageable pageable) {
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            return postService.searchPostsByGallery(galleryId, keyword.trim(), pageable);
        }
        
        if ("recommend".equals(sort)) {
            return postService.getPostsByGallerySortedByRecommend(galleryId, pageable);
        }
        
        if ("view".equals(sort)) {
            return postService.getPostsByGallerySortedByViewCount(galleryId, pageable);
        }
        
        return postService.getPostsByGallery(galleryId, pageable);
    }
    
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 정보를 조회합니다 (조회수 +1)")
    public PostResponse getPost(@PathVariable Long galleryId, @PathVariable Long postId) {
        return postService.getPost(postId);
    }
    
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다 (비밀번호 확인 필요)")
    public PostResponse updatePost(@PathVariable Long galleryId, @PathVariable Long postId, @Valid @RequestBody PostUpdateRequest request) {
        return postService.updatePost(postId, request);
    }
    
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다 (비밀번호 확인 필요)")
    public void deletePost(@PathVariable Long galleryId, @PathVariable Long postId, @Valid @RequestBody PostDeleteRequest request) {
        postService.deletePost(postId, request);
    }
}