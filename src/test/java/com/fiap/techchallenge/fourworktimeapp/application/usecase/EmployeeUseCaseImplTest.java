package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository.EmployeeRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeUseCaseImplTest {

    @Mock
    EmployeeRepositoryImpl repository;
    @Mock
    PasswordEncoder passwordEncoder;


    @Test
    void shouldRegisterEmployeeCorrectly() {
        // Arrange
        var employeeUseCase = new EmployeeUseCaseImpl(repository, passwordEncoder);

        var employee = new Employee();
        employee.setPassword("DummyPassword");

        var now = LocalDateTime.now();
        var encodedPassword = "EncodedDummyPassword";
        var expected = new Employee();
        expected.setPassword(encodedPassword);
        expected.setCreatedAt(now);
        expected.setUpdatedAt(now);

        when(repository.registerEmployee(any())).thenReturn(expected);
        when(passwordEncoder.encode(employee.getPassword())).thenReturn(encodedPassword);

        // Act
        var result = employeeUseCase.registerEmployee(employee);

        // Assert
        assertNotNull(result);
        assertEquals(expected, result);
        assertEquals(expected.getCreatedAt(), result.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), result.getUpdatedAt());
        assertEquals(expected.getPassword(), result.getPassword());
    }
}