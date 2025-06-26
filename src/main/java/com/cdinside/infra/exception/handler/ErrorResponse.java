package com.cdinside.infra.exception.handler;

import com.cdinside.global.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {
    
    private String message;
    private String code;
    private int status;
    private Map<String, Object> errors;
    
    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.name())
                .status(errorCode.getHttpStatus().value())
                .errors(new HashMap<>())
                .build();
    }
    
    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        return ErrorResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.name())
                .status(errorCode.getHttpStatus().value())
                .errors(errors)
                .build();
    }
}