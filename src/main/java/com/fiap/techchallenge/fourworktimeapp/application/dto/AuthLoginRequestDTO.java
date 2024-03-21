package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fiap.techchallenge.fourworktimeapp.domain.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequestDTO {
    private String registry;
    private String password;

    public Employee toEmployee() {
        return Employee.builder()
                .registry(this.registry)
                .password(this.password)
                .build();
    }
}
