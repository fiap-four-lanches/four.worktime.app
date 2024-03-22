package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "employees")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    private String registry;

    private String password;

    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Employee toEmployee() {
        return Employee.builder()
                .id(id)
                .registry(registry)
                .password(password)
                .role(role)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static EmployeeJpaEntity fromEmployee(Employee employee) {
        var jpaEntity = EmployeeJpaEntity.builder()
                .registry(employee.getRegistry())
                .password(employee.getPassword())
                .role(employee.getRole())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();

        if (employee.getId() != null) {
            jpaEntity.setId(employee.getId());
        }

        return jpaEntity;
    }
}
