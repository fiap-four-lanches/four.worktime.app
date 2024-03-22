package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.EmployeeJpaRepository;
import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.EmployeeJpaEntity;
import com.fiap.techchallenge.fourworktimeapp.application.exception.EmployeeNotFoundException;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


@Repository
@AllArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private EmployeeJpaRepository jpaRepository;

    public Employee findEmployeeByRegistry(String registry) throws UsernameNotFoundException {
        var found = jpaRepository.findEmployeeJpaEntityByRegistry(registry);

        if (found.isEmpty()) {
            throw new EmployeeNotFoundException("employee not found");
        }

        return found.get().toEmployee();
    }

    @Override
    public Employee registerEmployee(Employee employee) {
        var entity = EmployeeJpaEntity.fromEmployee(employee);
        var saved = jpaRepository.save(entity);
        return saved.toEmployee();
    }

}
