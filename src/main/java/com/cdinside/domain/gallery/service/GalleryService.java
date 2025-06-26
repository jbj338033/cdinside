package com.cdinside.domain.gallery.service;

import com.cdinside.domain.gallery.domain.entity.GalleryEntity;
import com.cdinside.domain.gallery.domain.enums.GalleryStatus;
import com.cdinside.domain.gallery.domain.repository.GalleryRepository;
import com.cdinside.domain.gallery.dto.GalleryCreateRequest;
import com.cdinside.domain.gallery.dto.GalleryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GalleryService {
    
    private final GalleryRepository galleryRepository;
    
    @Transactional
    public GalleryResponse createGallery(GalleryCreateRequest request) {
        if (galleryRepository.findByGalleryName(request.getGalleryName()).isPresent()) {
            throw new RuntimeException("이미 존재하는 갤러리 이름입니다");
        }
        
        GalleryEntity gallery = GalleryEntity.builder()
                .galleryName(request.getGalleryName())
                .description(request.getDescription())
                .build();
        
        GalleryEntity savedGallery = galleryRepository.save(gallery);
        return GalleryResponse.from(savedGallery);
    }
    
    public List<GalleryResponse> getAllGalleries() {
        return galleryRepository.findByStatusOrderByCreatedAtDesc(GalleryStatus.ACTIVE)
                .stream()
                .map(GalleryResponse::from)
                .toList();
    }
    
    public GalleryResponse getGalleryById(Long galleryId) {
        GalleryEntity gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new RuntimeException("갤러리를 찾을 수 없습니다"));
        
        return GalleryResponse.from(gallery);
    }
    
    public List<GalleryResponse> getPopularGalleries() {
        return galleryRepository.findByStatusOrderByPostCountDesc(GalleryStatus.ACTIVE)
                .stream()
                .map(GalleryResponse::from)
                .toList();
    }
}