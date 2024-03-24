package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class TimesheetResponseDTO extends RepresentationModel<TimesheetResponseDTO> {

    private List<TimesheetDailyEntryDTO> data;

    @JsonCreator
    public TimesheetResponseDTO(@JsonProperty("data") List<TimesheetDailyEntryDTO> data) {
        this.data = data;
    }

}
