package org.example.exp.hande;

import org.example.dto.ApiResponse;
import org.example.exp.AppBadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleController {

    @ExceptionHandler({IllegalArgumentException.class, AppBadException.class})
    public ResponseEntity<ApiResponse> handle(AppBadException e) {
        return ResponseEntity.ok(ApiResponse.bad(e.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handle(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
