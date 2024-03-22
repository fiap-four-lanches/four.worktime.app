package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClockEntryResponseDataDTO {
    private Long employeeId;
    private LocalDateTime clockedTime;
    private ClockType clockType;
    private boolean wasManuallyModified;

    public static ClockEntryResponseDataDTO fromClock(Clock clock) {
        return ClockEntryResponseDataDTO.builder()
                .employeeId(clock.getEmployeeId())
                .clockedTime(clock.getClockedTime())
                .clockType(clock.getClockType())
                .wasManuallyModified(clock.isWasManuallyModified())
                .build();
    }
}
