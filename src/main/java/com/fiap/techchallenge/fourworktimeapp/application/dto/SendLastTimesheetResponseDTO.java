package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class SendLastTimesheetResponseDTO extends RepresentationModel<TimesheetResponseDTO> {

    @JsonCreator
    public SendLastTimesheetResponseDTO() {
    }

}
