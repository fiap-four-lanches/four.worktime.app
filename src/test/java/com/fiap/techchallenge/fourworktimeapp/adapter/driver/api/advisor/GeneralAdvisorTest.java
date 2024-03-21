package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.advisor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GeneralAdvisorTest {
    @InjectMocks
    private GeneralAdvisor advisor;

    @Mock
    private WebRequest webRequest;

    @Test
    public void shouldHandleInternalServerErrorException() {
        // Arrange
        var ex = new RuntimeException("error");
        var expectedError = new ApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "an error happened");


        // Act
        ResponseEntity<ApiErrorMessage> actualResponse = advisor.handleInternalServerErrorException(ex, webRequest);

        // Assert
        var expectedResponse = new ResponseEntity<>(expectedError, HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(expectedResponse, actualResponse);
    }
}