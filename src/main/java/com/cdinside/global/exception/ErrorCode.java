package com.cdinside.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "유효하지 않은 입력값입니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다"),
    
    // Gallery
    GALLERY_NOT_FOUND(HttpStatus.NOT_FOUND, "갤러리를 찾을 수 없습니다"),
    GALLERY_NAME_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 갤러리 이름입니다"),
    
    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다"),
    POST_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    
    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다"),
    COMMENT_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    
    // Recommendation
    RECOMMENDATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 추천/비추천하셨습니다");
    
    private final HttpStatus httpStatus;
    private final String message;
}