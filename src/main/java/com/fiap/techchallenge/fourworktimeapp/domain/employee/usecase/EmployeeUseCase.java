package com.fiap.techchallenge.fourworktimeapp.domain.employee.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.employee.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeUseCase {
    Employee registerEmployee(Employee employee);
}
