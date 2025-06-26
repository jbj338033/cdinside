package com.cdinside.domain.gallery.domain.entity;

import com.cdinside.domain.gallery.domain.enums.GalleryStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "galleries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GalleryEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_id")
    private Long galleryId;
    
    @Column(name = "gallery_name", nullable = false, length = 100)
    private String galleryName;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "post_count", nullable = false)
    private Integer postCount = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GalleryStatus status = GalleryStatus.ACTIVE;
    
    @Builder
    public GalleryEntity(String galleryName, String description) {
        this.galleryName = galleryName;
        this.description = description;
        this.postCount = 0;
        this.status = GalleryStatus.ACTIVE;
    }
    
    public void incrementPostCount() {
        this.postCount++;
    }
    
    public void decrementPostCount() {
        if (this.postCount > 0) {
            this.postCount--;
        }
    }
}