package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.EmployeeJpaRepository;
import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.EmployeeJpaEntity;
import com.fiap.techchallenge.fourworktimeapp.application.exception.EmployeeNotFoundException;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeRepositoryImplTest {

    @Mock
    EmployeeJpaRepository jpaRepository;

    @Test
    public void shouldFindEmployeeByRegistry() {
        // Arrange
        String registries = "123";
        var employee = new EmployeeJpaEntity();

        // Act
        when(jpaRepository.findEmployeeJpaEntityByRegistry(registries)).thenReturn(Optional.of(employee));
        var employeeRepository = new EmployeeRepositoryImpl(jpaRepository);

        // Assert
        assertNotNull(employeeRepository.findEmployeeByRegistry(registries));
    }

    @Test
    public void shouldThrowNotFindWhenTryingToFindEmployeeByRegistry() {
        // Arrange
        String registries = "123";
        var employeeRepository = new EmployeeRepositoryImpl(jpaRepository);

        // Act
        when(jpaRepository.findEmployeeJpaEntityByRegistry(registries)).thenReturn(Optional.empty());

        // Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeRepository.findEmployeeByRegistry(registries));
    }

    @Test
    public void shouldRegisterEmployeeSuccessful() {
        // Arrange
        var employee = new Employee();
        var employeeJpaEntity = EmployeeJpaEntity.fromEmployee(employee);

        // Act
        when(jpaRepository.save(any())).thenReturn(employeeJpaEntity);
        var employeeRepository = new EmployeeRepositoryImpl(jpaRepository);

        // Assert
        var registered = employeeRepository.registerEmployee(employee);
        Assertions.assertNotNull(registered, "Registered employee must not be null");
    }

    @Test
    public void shouldThrowUpSaveExceptionWhenRegisterEmployeeFail() {
        // Arrange
        var employee = new Employee();
        var employeeRepository = new EmployeeRepositoryImpl(jpaRepository);

        // Act
        when(jpaRepository.save(any())).thenThrow(new RuntimeException("Unexpected exception"));

        // Assert
        Assertions.assertThrows(RuntimeException.class, () ->
                        employeeRepository.registerEmployee(employee),
                "Should throw Runtime Exception");
    }
}