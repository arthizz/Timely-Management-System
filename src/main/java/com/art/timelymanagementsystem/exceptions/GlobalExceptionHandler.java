package com.art.timelymanagementsystem.exceptions;

import com.art.timelymanagementsystem.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException( MethodArgumentNotValidException e ){

        var fieldError = e.getBindingResult().getFieldError();

        String message = fieldError != null ? fieldError.getDefaultMessage() : "Validation failed";

        return ResponseEntity.badRequest().body(new ErrorResponseDto(400, message));

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(404, e.getMessage()));

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto(500, "Something went Wrong"));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(Exception e){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), "Invalid User Credentials"));

    }

}
