package com.cdinside.domain.post.domain.entity;

import com.cdinside.domain.gallery.domain.entity.BaseEntity;
import com.cdinside.domain.gallery.domain.entity.GalleryEntity;
import com.cdinside.domain.post.domain.enums.PostStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id", nullable = false)
    private GalleryEntity gallery;
    
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "author_name", nullable = false, length = 50)
    private String authorName;
    
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    
    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;
    
    @Column(name = "recommend_count", nullable = false)
    private Integer recommendCount = 0;
    
    @Column(name = "unrecommend_count", nullable = false)
    private Integer unrecommendCount = 0;
    
    @Column(name = "comment_count", nullable = false)
    private Integer commentCount = 0;
    
    @Column(name = "is_notice", nullable = false)
    private Boolean isNotice = false;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status = PostStatus.ACTIVE;
    
    @Builder
    public PostEntity(GalleryEntity gallery, String title, String content, String authorName, String password, Boolean isNotice) {
        this.gallery = gallery;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.password = password;
        this.viewCount = 0;
        this.recommendCount = 0;
        this.unrecommendCount = 0;
        this.commentCount = 0;
        this.isNotice = isNotice != null ? isNotice : false;
        this.status = PostStatus.ACTIVE;
    }
    
    public void incrementViewCount() {
        this.viewCount++;
    }
    
    public void incrementRecommendCount() {
        this.recommendCount++;
    }
    
    public void decrementRecommendCount() {
        if (this.recommendCount > 0) {
            this.recommendCount--;
        }
    }
    
    public void incrementUnrecommendCount() {
        this.unrecommendCount++;
    }
    
    public void decrementUnrecommendCount() {
        if (this.unrecommendCount > 0) {
            this.unrecommendCount--;
        }
    }
    
    public void incrementCommentCount() {
        this.commentCount++;
    }
    
    public void decrementCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }
    
    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    public void delete() {
        this.status = PostStatus.DELETED;
    }
    
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}