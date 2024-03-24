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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeDetailServiceUseCaseTest {

    @Mock
    private EmployeeRepositoryImpl employeeRepository;

    @InjectMocks
    private EmployeeDetailServiceUseCase employeeDetailServiceUseCase;

    @Test
    public void shouldLoadUserByUsernameTestWhenEmployeeFoundWithRegistry() {
        // Arrange
        Employee employee = Employee.builder()
                .id(1L)
                .username("the_employee")
                .registry("reg123456")
                .password("password")
                .role(Role.WORKER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        when(employeeRepository.findEmployeeToAuthenticate("reg123456"))
                .thenReturn(employee);
        var userDetails = employeeDetailServiceUseCase.loadUserByUsername("reg123456");

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(employee.getRegistry(), userDetails.getUsername());
        Assertions.assertEquals(employee.getPassword(), userDetails.getPassword());
    }

    @Test
    public void shouldThrowExceptionWhileLoadUserByUsernameTestWhenEmployeeNotFound() {
        when(employeeRepository.findEmployeeToAuthenticate("reg123456")).thenThrow(new UsernameNotFoundException("username not found"));

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            employeeDetailServiceUseCase.loadUserByUsername("reg123456");
        });
    }
}