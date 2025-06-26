package com.cdinside.domain.recommendation.domain.entity;

import com.cdinside.domain.gallery.domain.entity.BaseEntity;
import com.cdinside.domain.post.domain.entity.PostEntity;
import com.cdinside.domain.recommendation.domain.enums.RecommendationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommendations", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "ip_address"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long recommendationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;
    
    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "recommendation_type", nullable = false)
    private RecommendationType recommendationType;
    
    @Builder
    public RecommendationEntity(PostEntity post, String ipAddress, RecommendationType recommendationType) {
        this.post = post;
        this.ipAddress = ipAddress;
        this.recommendationType = recommendationType;
    }
    
    public void updateRecommendationType(RecommendationType recommendationType) {
        this.recommendationType = recommendationType;
    }
}