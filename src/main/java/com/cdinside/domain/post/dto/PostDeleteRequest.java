package com.cdinside.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDeleteRequest {
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    
    public PostDeleteRequest(String password) {
        this.password = password;
    }
}