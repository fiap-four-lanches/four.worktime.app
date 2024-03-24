package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthLoginRequestDTO {
    private String username;
    private String registry;
    private String password;

    public Employee toEmployee() {
        return Employee.builder()
                .username(this.username)
                .registry(this.registry)
                .password(this.password)
                .build();
    }
}
