package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.controller;

import com.fiap.techchallenge.fourworktimeapp.application.dto.*;
import com.fiap.techchallenge.fourworktimeapp.domain.usecase.ClockUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("v1/clock")
@AllArgsConstructor
public class ClockController {

    private ClockUseCase useCase;

    @ResponseBody
    @PostMapping(value = "" , produces = "application/json")
    public ResponseEntity<ClockEntryResponseDTO> clockTime(@RequestBody ClockEntryRequestDTO request) {
        var clockedTime = useCase.clockInOrClockOut(request.toEntry());

        var response = new ClockEntryResponseDTO(ClockEntryResponseDataDTO.fromClock(clockedTime));
        response.add(linkTo(methodOn(ClockController.class).clockTime(request)).withSelfRel());

        return ResponseEntity.ok(response);
    }
}
