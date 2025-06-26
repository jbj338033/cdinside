package com.cdinside.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
    
    @NotBlank(message = "제목은 필수입니다")
    @Size(min = 1, max = 200, message = "제목은 1-200자 사이여야 합니다")
    private String title;
    
    @NotBlank(message = "내용은 필수입니다")
    private String content;
    
    @NotBlank(message = "작성자명은 필수입니다")
    @Size(min = 1, max = 50, message = "작성자명은 1-50자 사이여야 합니다")
    private String authorName;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 4, max = 20, message = "비밀번호는 4-20자 사이여야 합니다")
    private String password;
    
    private Boolean isNotice = false;
    
    public PostCreateRequest(String title, String content, String authorName, String password, Boolean isNotice) {
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.password = password;
        this.isNotice = isNotice;
    }
}