package com.cdinside.domain.gallery.controller;

import com.cdinside.domain.gallery.service.GalleryStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/galleries/{galleryId}/statistics")
@RequiredArgsConstructor
@Tag(name = "Gallery Statistics", description = "갤러리 통계 API")
public class GalleryStatisticsController {
    
    private final GalleryStatisticsService galleryStatisticsService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "갤러리 통계 조회", description = "특정 갤러리의 통계 정보를 조회합니다")
    public Map<String, Object> getGalleryStatistics(@PathVariable Long galleryId) {
        return galleryStatisticsService.getGalleryStatistics(galleryId);
    }
}