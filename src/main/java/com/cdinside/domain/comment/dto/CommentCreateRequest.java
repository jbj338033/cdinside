package com.cdinside.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {
    
    @NotBlank(message = "댓글 내용은 필수입니다")
    @Size(min = 1, max = 1000, message = "댓글 내용은 1-1000자 사이여야 합니다")
    private String content;
    
    @NotBlank(message = "작성자명은 필수입니다")
    @Size(min = 1, max = 50, message = "작성자명은 1-50자 사이여야 합니다")
    private String authorName;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 4, max = 20, message = "비밀번호는 4-20자 사이여야 합니다")
    private String password;
    
    public CommentCreateRequest(String content, String authorName, String password) {
        this.content = content;
        this.authorName = authorName;
        this.password = password;
    }
}