package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterRequestDTO {
    private String username;
    private String registry;
    private String password;

    public Employee toWorkerEmployee() {
        return Employee.builder()
                .username(this.username)
                .registry(this.registry)
                .password(this.password)
                .role(Role.WORKER)
                .build();
    }
}
