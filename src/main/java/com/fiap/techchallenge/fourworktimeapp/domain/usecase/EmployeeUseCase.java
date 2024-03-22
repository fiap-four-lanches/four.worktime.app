package com.fiap.techchallenge.fourworktimeapp.domain.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeUseCase {
    Employee registerEmployee(Employee employee);
}
