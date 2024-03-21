package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.advisor;

import com.fiap.techchallenge.fourworktimeapp.application.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GeneralAdvisor {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorMessage> handleInternalServerErrorException(
            RuntimeException ex, WebRequest request) {

        var errorMessage = new ApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "an error happened");

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
