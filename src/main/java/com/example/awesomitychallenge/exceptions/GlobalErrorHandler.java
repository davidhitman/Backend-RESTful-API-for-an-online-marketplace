
package com.example.awesomitychallenge.exceptions;

import com.example.awesomitychallenge.controllers.GenericResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value ={RuntimeException.class, UsernameNotFoundException.class})
    public ResponseEntity<GenericResponse<?>> handleException(Exception e) {
        var response = new GenericResponse<>(e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<GenericResponse<?>> handleExpiredJwtException(ExpiredJwtException ex) {
        GenericResponse<?> response = new GenericResponse<>( ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        GenericResponse<?> response = new GenericResponse<>(errorMessage, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}