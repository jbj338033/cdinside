package com.cdinside.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteRequest {
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    public CommentDeleteRequest(String password) {
        this.password = password;
    }
}