package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository.EmployeeRepositoryImpl;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.EmployeeRepository;
import com.fiap.techchallenge.fourworktimeapp.domain.usecase.EmployeeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class EmployeeUseCaseImpl implements EmployeeUseCase {

    private EmployeeRepository repository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Employee registerEmployee(Employee employee) {
        var now = LocalDateTime.now();
        employee.setCreatedAt(now);
        employee.setUpdatedAt(now);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return repository.registerEmployee(employee);
    }
}
