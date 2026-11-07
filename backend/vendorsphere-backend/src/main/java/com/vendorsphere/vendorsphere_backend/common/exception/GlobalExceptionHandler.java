package com.vendorsphere.vendorsphere_backend.common.exception;

import com.vendorsphere.vendorsphere_backend.Auth.Exception.UserAlreadyExistsException;
import com.vendorsphere.vendorsphere_backend.Vendor.exception.VendorAlreadyExistsException;
import com.vendorsphere.vendorsphere_backend.Vendor.exception.VendorNotFoundException;
import com.vendorsphere.vendorsphere_backend.common.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex){
        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();
        ErrorResponseDto response =
                ErrorResponseDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(UserAlreadyExistsException ex){

        ErrorResponseDto response =
                ErrorResponseDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .message(ex.getMessage())
                        .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
    @ExceptionHandler(
            VendorAlreadyExistsException.class
    )
    public ResponseEntity<ErrorResponseDto>
    handleVendorAlreadyExists(
            VendorAlreadyExistsException ex
    ) {

        ErrorResponseDto response =
                ErrorResponseDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .message(ex.getMessage())
                        .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
    @ExceptionHandler(
            VendorNotFoundException.class
    )
    public ResponseEntity<ErrorResponseDto>
    handleVendorNotFound(
            VendorNotFoundException ex
    ) {

        ErrorResponseDto response =
                ErrorResponseDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

}
