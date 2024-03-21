package com.fiap.techchallenge.fourworktimeapp.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginResponseDTO {
    private String registry;
    private String token;
}
