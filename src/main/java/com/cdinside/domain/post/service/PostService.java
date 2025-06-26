package com.cdinside.domain.post.service;

import com.cdinside.domain.gallery.domain.entity.GalleryEntity;
import com.cdinside.domain.gallery.domain.repository.GalleryRepository;
import com.cdinside.domain.post.domain.entity.PostEntity;
import com.cdinside.domain.post.domain.enums.PostStatus;
import com.cdinside.domain.post.domain.repository.PostRepository;
import com.cdinside.domain.post.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    
    private final PostRepository postRepository;
    private final GalleryRepository galleryRepository;
    
    @Transactional
    public PostResponse createPost(Long galleryId, PostCreateRequest request) {
        GalleryEntity gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new RuntimeException("갤러리를 찾을 수 없습니다"));
        
        PostEntity post = PostEntity.builder()
                .gallery(gallery)
                .title(request.getTitle())
                .content(request.getContent())
                .authorName(request.getAuthorName())
                .password(request.getPassword())
                .isNotice(request.getIsNotice())
                .build();
        
        PostEntity savedPost = postRepository.save(post);
        gallery.incrementPostCount();
        
        return PostResponse.from(savedPost);
    }
    
    public Page<PostResponse> getPostsByGallery(Long galleryId, Pageable pageable) {
        return postRepository.findByGalleryGalleryIdAndStatusOrderByCreatedAtDesc(galleryId, PostStatus.ACTIVE, pageable)
                .map(PostResponse::from);
    }
    
    public Page<PostResponse> searchPostsByGallery(Long galleryId, String keyword, Pageable pageable) {
        return postRepository.findByGalleryIdAndStatusAndKeyword(galleryId, PostStatus.ACTIVE, keyword, pageable)
                .map(PostResponse::from);
    }
    
    @Transactional
    public PostResponse getPost(Long postId) {
        PostEntity post = postRepository.findByPostIdAndStatus(postId, PostStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
        
        post.incrementViewCount();
        return PostResponse.from(post);
    }
    
    @Transactional
    public PostResponse updatePost(Long postId, PostUpdateRequest request) {
        PostEntity post = postRepository.findByPostIdAndStatus(postId, PostStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
        
        if (!post.verifyPassword(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        
        post.updateContent(request.getTitle(), request.getContent());
        return PostResponse.from(post);
    }
    
    @Transactional
    public void deletePost(Long postId, PostDeleteRequest request) {
        PostEntity post = postRepository.findByPostIdAndStatus(postId, PostStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
        
        if (!post.verifyPassword(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        
        post.delete();
        post.getGallery().decrementPostCount();
    }
    
    public Page<PostResponse> getHotPosts(Integer minRecommendCount, Pageable pageable) {
        return postRepository.findHotPosts(PostStatus.ACTIVE, minRecommendCount != null ? minRecommendCount : 5, pageable)
                .map(PostResponse::from);
    }
    
    public Page<PostResponse> getPostsSortedByRecommend(Pageable pageable) {
        return postRepository.findByStatusOrderByRecommendCountDesc(PostStatus.ACTIVE, pageable)
                .map(PostResponse::from);
    }
    
    public Page<PostResponse> getPostsSortedByViewCount(Pageable pageable) {
        return postRepository.findByStatusOrderByViewCountDesc(PostStatus.ACTIVE, pageable)
                .map(PostResponse::from);
    }
    
    public Page<PostResponse> getPostsByGallerySortedByRecommend(Long galleryId, Pageable pageable) {
        return postRepository.findByGalleryGalleryIdAndStatusOrderByRecommendCountDesc(galleryId, PostStatus.ACTIVE, pageable)
                .map(PostResponse::from);
    }
    
    public Page<PostResponse> getPostsByGallerySortedByViewCount(Long galleryId, Pageable pageable) {
        return postRepository.findByGalleryGalleryIdAndStatusOrderByViewCountDesc(galleryId, PostStatus.ACTIVE, pageable)
                .map(PostResponse::from);
    }
}