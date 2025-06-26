package com.cdinside.domain.gallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GalleryCreateRequest {
    
    @NotBlank(message = "갤러리 이름은 필수입니다")
    @Size(min = 2, max = 100, message = "갤러리 이름은 2-100자 사이여야 합니다")
    private String galleryName;
    
    @Size(max = 500, message = "설명은 500자 이하여야 합니다")
    private String description;
    
    public GalleryCreateRequest(String galleryName, String description) {
        this.galleryName = galleryName;
        this.description = description;
    }
}