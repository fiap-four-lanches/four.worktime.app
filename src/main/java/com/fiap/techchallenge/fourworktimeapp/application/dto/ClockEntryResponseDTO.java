package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ClockEntryResponseDTO extends RepresentationModel<ClockEntryResponseDTO> {
    private ClockEntryResponseDataDTO data;

    @JsonCreator
    public ClockEntryResponseDTO(@JsonProperty("data") ClockEntryResponseDataDTO data) {
        this.data = data;
    }
}
