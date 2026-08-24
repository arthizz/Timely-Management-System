package com.art.timelymanagementsystem.exceptions;

import com.art.timelymanagementsystem.dto.ErrorResponseDto;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound( UserNotFoundException e ){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(404, e.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException( MethodArgumentNotValidException e ){

        String message = e.getBindingResult().getFieldError().getDefaultMessage();

        return ResponseEntity.badRequest().body(new ErrorResponseDto(400, message));

    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCompanyNotFoundException(CompanyNotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(404, e.getMessage()));

    }

    @ExceptionHandler(CompanyRoleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCompanyRoleNotFoundException(CompanyRoleNotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(404, e.getMessage()));

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

}
