package com.cdinside.domain.gallery.controller;

import com.cdinside.domain.gallery.dto.GalleryCreateRequest;
import com.cdinside.domain.gallery.dto.GalleryResponse;
import com.cdinside.domain.gallery.service.GalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/galleries")
@RequiredArgsConstructor
@Tag(name = "Gallery", description = "갤러리 API")
public class GalleryController {
    
    private final GalleryService galleryService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "갤러리 생성", description = "새로운 갤러리를 생성합니다")
    public GalleryResponse createGallery(@Valid @RequestBody GalleryCreateRequest request) {
        return galleryService.createGallery(request);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "갤러리 목록 조회", description = "모든 갤러리 목록을 조회합니다")
    public List<GalleryResponse> getAllGalleries() {
        return galleryService.getAllGalleries();
    }
    
    @GetMapping("/{galleryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "갤러리 상세 조회", description = "특정 갤러리 정보를 조회합니다")
    public GalleryResponse getGallery(@PathVariable Long galleryId) {
        return galleryService.getGalleryById(galleryId);
    }
    
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "인기 갤러리 조회", description = "게시글 수 기준 인기 갤러리를 조회합니다")
    public List<GalleryResponse> getPopularGalleries() {
        return galleryService.getPopularGalleries();
    }
}