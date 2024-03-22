package com.fiap.techchallenge.fourworktimeapp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Clock {
    private Long id;
    private Long employeeId;
    private LocalDateTime clockedTime;
    private ClockType clockType;
    private boolean wasManuallyModified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
