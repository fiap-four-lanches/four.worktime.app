package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.advisor;

import com.fiap.techchallenge.fourworktimeapp.application.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthAdvisorTest {
    @InjectMocks
    private AuthAdvisor advisor;

    @Mock
    private WebRequest webRequest;

    @Test
    public void shouldHandleEmployeeNotFoundException() {
        // Arrange
        var ex = new EmployeeNotFoundException("employee not found");
        var expectedError = new ApiErrorMessage(HttpStatus.NOT_FOUND, "employee not found");

        // Act
        ResponseEntity<ApiErrorMessage> actualResponse = advisor.handleEmployeeNotFoundException(ex, webRequest);

        // Assert
        var expectedResponse = new ResponseEntity<>(expectedError, HttpStatus.NOT_FOUND);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldHandleBadCredentialsException() {
        // Arrange
        var ex = new BadCredentialsException("invalid registry or password");
        var expectedError = new ApiErrorMessage(HttpStatus.BAD_REQUEST, "invalid registry or password");

        // Act
        ResponseEntity<ApiErrorMessage> actualResponse = advisor.handleBadCredentialsException(ex, webRequest);

        // Assert
        var expectedResponse = new ResponseEntity<>(expectedError, HttpStatus.BAD_REQUEST);
        assertEquals(expectedResponse, actualResponse);
    }
}