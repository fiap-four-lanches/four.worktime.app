package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fiap.techchallenge.fourworktimeapp.domain.employee.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.employee.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterRequestDTO {
    private String registry;
    private String password;

    public Employee toWorkerEmployee() {
        return Employee.builder()
                .registry(this.registry)
                .password(this.password)
                .role(Role.WORKER)
                .build();
    }
}
