package com.cdinside.domain.gallery.domain.repository;

import com.cdinside.domain.gallery.domain.entity.GalleryEntity;
import com.cdinside.domain.gallery.domain.enums.GalleryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryEntity, Long> {
    
    List<GalleryEntity> findByStatusOrderByCreatedAtDesc(GalleryStatus status);
    
    Optional<GalleryEntity> findByGalleryName(String galleryName);
    
    @Query("SELECT g FROM GalleryEntity g WHERE g.status = :status ORDER BY g.postCount DESC")
    List<GalleryEntity> findByStatusOrderByPostCountDesc(@Param("status") GalleryStatus status);
}