package com.cdinside.domain.post.dto;

import com.cdinside.domain.gallery.dto.GalleryResponse;
import com.cdinside.domain.post.domain.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {
    
    private Long postId;
    private String title;
    private String content;
    private String authorName;
    private Integer viewCount;
    private Integer recommendCount;
    private Integer unrecommendCount;
    private Integer commentCount;
    private Boolean isNotice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GalleryInfo gallery;
    
    @Getter
    @Builder
    public static class GalleryInfo {
        private Long galleryId;
        private String galleryName;
        
        public static GalleryInfo from(com.cdinside.domain.gallery.domain.entity.GalleryEntity gallery) {
            return GalleryInfo.builder()
                    .galleryId(gallery.getGalleryId())
                    .galleryName(gallery.getGalleryName())
                    .build();
        }
    }
    
    public static PostResponse from(PostEntity post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(post.getAuthorName())
                .viewCount(post.getViewCount())
                .recommendCount(post.getRecommendCount())
                .unrecommendCount(post.getUnrecommendCount())
                .commentCount(post.getCommentCount())
                .isNotice(post.getIsNotice())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .gallery(GalleryInfo.from(post.getGallery()))
                .build();
    }
}