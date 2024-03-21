package com.fiap.techchallenge.fourworktimeapp.domain.employee.repository;

import com.fiap.techchallenge.fourworktimeapp.domain.employee.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository {
    Employee findEmployeeByRegistry(String registry);

    Employee registerEmployee(Employee employee);
}
