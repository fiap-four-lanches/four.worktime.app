package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository.EmployeeRepositoryImpl;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class EmployeeDetailServiceUseCaseTest {

    @Mock
    private EmployeeRepositoryImpl employeeRepository;

    @InjectMocks
    private EmployeeDetailServiceUseCase employeeDetailServiceUseCase;

    /**
     * Test for loadUserByUsername method. Employee is found in the repository.
     */
    @Test
    public void shouldLoadUserByUsernameTestWhenEmployeeFound() {
        // Arrange
        var employee = new Employee(1L, "reg123456", "password", Role.WORKER, LocalDateTime.now(), LocalDateTime.now());

        // Act
        Mockito.when(employeeRepository.findEmployeeByRegistry("123456"))
                .thenReturn(employee);
        var userDetails = employeeDetailServiceUseCase.loadUserByUsername("123456");

        // Assert
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(employee.getRegistry(), userDetails.getUsername());
        Assertions.assertEquals(employee.getPassword(), userDetails.getPassword());
    }

    @Test
    public void shouldThrowExceptionWhileLoadUserByUsernameTestWhenEmployeeNotFound() {
        // Arrange & Act
        Mockito.when(employeeRepository.findEmployeeByRegistry("123456")).thenThrow(new UsernameNotFoundException("username not found"));

        // Assert
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            employeeDetailServiceUseCase.loadUserByUsername("123456");
        });
    }
}