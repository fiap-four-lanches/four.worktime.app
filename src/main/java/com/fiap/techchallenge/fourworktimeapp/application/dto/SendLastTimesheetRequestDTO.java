package com.fiap.techchallenge.fourworktimeapp.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendLastTimesheetRequestDTO {

    private Long employeeId;
    private String requesterEmail;

}