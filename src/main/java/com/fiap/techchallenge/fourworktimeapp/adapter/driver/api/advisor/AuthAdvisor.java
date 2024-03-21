package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.advisor;

import com.fiap.techchallenge.fourworktimeapp.application.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AuthAdvisor {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> handleEmployeeNotFoundException(
            EmployeeNotFoundException ex, WebRequest request) {
        var errorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorMessage> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        var errorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, "invalid registry or password");

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
