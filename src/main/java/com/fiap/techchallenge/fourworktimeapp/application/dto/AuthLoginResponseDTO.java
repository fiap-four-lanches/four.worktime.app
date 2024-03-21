package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthLoginResponseDTO extends RepresentationModel<AuthLoginResponseDTO> {
    private AuthLoginDataResponseDTO data;

    @JsonCreator
    public AuthLoginResponseDTO(@JsonProperty("data") AuthLoginDataResponseDTO data) {
        this.data = data;
    }
}
