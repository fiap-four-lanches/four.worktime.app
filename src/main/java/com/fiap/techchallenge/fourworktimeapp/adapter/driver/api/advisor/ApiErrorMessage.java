package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.advisor;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
public class ApiErrorMessage {

    private HttpStatus status;
    private List<String> errors;

    public ApiErrorMessage(HttpStatus status, String error) {
        this.status = status;
        errors = Collections.singletonList(error);
    }

}