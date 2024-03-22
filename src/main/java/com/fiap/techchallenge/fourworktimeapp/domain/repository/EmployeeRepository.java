package com.fiap.techchallenge.fourworktimeapp.domain.repository;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository {
    Employee findEmployeeByRegistry(String registry);

    Employee registerEmployee(Employee employee);
}
