package com.cdinside.domain.comment.domain.entity;

import com.cdinside.domain.comment.domain.enums.CommentStatus;
import com.cdinside.domain.gallery.domain.entity.BaseEntity;
import com.cdinside.domain.post.domain.entity.PostEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "author_name", nullable = false, length = 50)
    private String authorName;
    
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CommentStatus status = CommentStatus.ACTIVE;
    
    @Builder
    public CommentEntity(PostEntity post, String content, String authorName, String password) {
        this.post = post;
        this.content = content;
        this.authorName = authorName;
        this.password = password;
        this.status = CommentStatus.ACTIVE;
    }
    
    public void delete() {
        this.status = CommentStatus.DELETED;
    }
    
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}