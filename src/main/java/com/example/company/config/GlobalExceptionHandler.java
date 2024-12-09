package com.example.company.config;

import com.example.company.dto.response.ErrorResponse;
import com.example.company.exception.ApplicationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE = "Something went wrong";

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ErrorResponse<String>> applicationException(ApplicationException applicationException) {
        return ResponseEntity.status(applicationException.getHttpStatus())
                .body(ErrorResponse.<String>builder()
                        .message(applicationException.getErrorMessage().orElse(DEFAULT_ERROR_MESSAGE))
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> methodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        var errors = new HashMap<String, String>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(methodArgumentNotValidException.getStatusCode())
                .body(ErrorResponse.<Map<String, String>>builder()
                        .message(errors)
                        .build());
    }

    @ExceptionHandler(value = RestClientException.class)
    public ResponseEntity<ErrorResponse<String>> applicationException(RestClientException restClientException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.<String>builder()
                        .message("Api provider is currently unavailable: " + restClientException.getMessage())
                        .build());
    }
}
