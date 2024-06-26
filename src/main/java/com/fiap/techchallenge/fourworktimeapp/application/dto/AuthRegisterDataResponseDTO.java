package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuthRegisterDataResponseDTO {
    private String registry;
    private Long employeeId;
    private LocalDateTime createdAt;

    public static AuthRegisterDataResponseDTO fromEmployee(Employee employee) {
        return new AuthRegisterDataResponseDTO(employee.getRegistry(), employee.getId(), employee.getCreatedAt());
    }
}
