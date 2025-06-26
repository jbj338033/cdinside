package com.cdinside.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {
    
    @NotBlank(message = "제목은 필수입니다")
    @Size(min = 1, max = 200, message = "제목은 1-200자 사이여야 합니다")
    private String title;
    
    @NotBlank(message = "내용은 필수입니다")
    private String content;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    public PostUpdateRequest(String title, String content, String password) {
        this.title = title;
        this.content = content;
        this.password = password;
    }
}