package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthRegisterResponseDTO extends RepresentationModel<AuthRegisterResponseDTO> {
    private AuthRegisterDataResponseDTO data;

    @JsonCreator
    public AuthRegisterResponseDTO(@JsonProperty("data") AuthRegisterDataResponseDTO data) {
        this.data = data;
    }
}
