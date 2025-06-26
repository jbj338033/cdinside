package com.cdinside.domain.gallery.service;

import com.cdinside.domain.comment.domain.enums.CommentStatus;
import com.cdinside.domain.comment.domain.repository.CommentRepository;
import com.cdinside.domain.gallery.domain.entity.GalleryEntity;
import com.cdinside.domain.gallery.domain.repository.GalleryRepository;
import com.cdinside.domain.post.domain.enums.PostStatus;
import com.cdinside.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GalleryStatisticsService {
    
    private final GalleryRepository galleryRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    
    public Map<String, Object> getGalleryStatistics(Long galleryId) {
        GalleryEntity gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new RuntimeException("갤러리를 찾을 수 없습니다"));
        
        long postCount = postRepository.countByGalleryGalleryIdAndStatus(galleryId, PostStatus.ACTIVE);
        
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("galleryId", gallery.getGalleryId());
        statistics.put("galleryName", gallery.getGalleryName());
        statistics.put("description", gallery.getDescription());
        statistics.put("postCount", postCount);
        statistics.put("createdAt", gallery.getCreatedAt());
        statistics.put("updatedAt", gallery.getUpdatedAt());
        
        return statistics;
    }
}