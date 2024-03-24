package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.EmployeeJpaRepository;
import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.EmployeeJpaEntity;
import com.fiap.techchallenge.fourworktimeapp.application.exception.EmployeeNotFoundException;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@AllArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private EmployeeJpaRepository jpaRepository;

    public Employee findEmployeeToAuthenticate(String registryOrUsername) throws UsernameNotFoundException {
        var foundWithUsername = jpaRepository.findEmployeeJpaEntityByUsername(registryOrUsername);

        if (foundWithUsername.isEmpty()) {
            log.warn("employee not found by username [registry_or_username:{}]", registryOrUsername);
            var foundWithRegistry = jpaRepository.findEmployeeJpaEntityByRegistry(registryOrUsername);

            if (foundWithRegistry.isEmpty()) {
                log.error("employee not found by registry nor username [registry_or_username:{}]", registryOrUsername);
                throw new EmployeeNotFoundException("employee not found");
            }

            return foundWithRegistry.get().toEmployee();
        }

        return foundWithUsername.get().toEmployee();
    }

    public Employee findEmployeeByRegistry(String registry) throws UsernameNotFoundException {
        var found = jpaRepository.findEmployeeJpaEntityByRegistry(registry);

        if (found.isEmpty()) {
            log.error("employee not found by registry [registry:{}]", registry);
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
