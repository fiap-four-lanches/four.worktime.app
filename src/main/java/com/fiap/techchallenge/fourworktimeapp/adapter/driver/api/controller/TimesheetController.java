package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.controller;

import com.fiap.techchallenge.fourworktimeapp.application.dto.TimesheetDailyEntryDTO;
import com.fiap.techchallenge.fourworktimeapp.application.dto.TimesheetResponseDTO;
import com.fiap.techchallenge.fourworktimeapp.domain.usecase.TimesheetUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@RequestMapping("v1/timesheet")
public class TimesheetController {

    private TimesheetUseCase useCase;

    @ResponseBody
    @RequestMapping(value = "{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<?> viewTimesheet(@PathVariable Long employeeId) {
        List<TimesheetDailyEntryDTO> openTimesheetEntries = useCase.viewTimesheet(employeeId).stream()
                .map(TimesheetDailyEntryDTO::fromTimesheetEntry).collect(Collectors.toList());

        var response = new TimesheetResponseDTO(openTimesheetEntries);
        response.add(linkTo(methodOn(TimesheetController.class).viewTimesheet(employeeId)).withSelfRel());

        return ResponseEntity.ok(openTimesheetEntries);
    }
}
