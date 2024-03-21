package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fiap.techchallenge.fourworktimeapp.domain.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuthRegisterResponseDTO {
    private String registry;
    private LocalDateTime createdAt;

    public static AuthRegisterResponseDTO fromEmployee(Employee employee) {
        return new AuthRegisterResponseDTO(employee.getRegistry(), employee.getCreatedAt());
    }
}
