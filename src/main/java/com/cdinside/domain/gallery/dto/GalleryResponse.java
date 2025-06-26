package com.cdinside.domain.gallery.dto;

import com.cdinside.domain.gallery.domain.entity.GalleryEntity;
import com.cdinside.domain.gallery.domain.enums.GalleryStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GalleryResponse {
    
    private Long galleryId;
    private String galleryName;
    private String description;
    private Integer postCount;
    private GalleryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static GalleryResponse from(GalleryEntity gallery) {
        return GalleryResponse.builder()
                .galleryId(gallery.getGalleryId())
                .galleryName(gallery.getGalleryName())
                .description(gallery.getDescription())
                .postCount(gallery.getPostCount())
                .status(gallery.getStatus())
                .createdAt(gallery.getCreatedAt())
                .updatedAt(gallery.getUpdatedAt())
                .build();
    }
}