package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.EmployeeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<EmployeeJpaEntity, Long> {
    Optional<EmployeeJpaEntity> findEmployeeJpaEntityByRegistry(String registry);
}
