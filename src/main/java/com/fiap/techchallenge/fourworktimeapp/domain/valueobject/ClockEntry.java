package com.fiap.techchallenge.fourworktimeapp.domain.valueobject;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClockEntry {
    private String registry;
    private Long employeeId;
    private LocalDateTime clockedTime;
    private boolean wasManuallyModified;

    public Clock toClock() {
        return Clock.builder()
                .employeeId(this.employeeId)
                .clockedTime(this.clockedTime)
                .wasManuallyModified(this.wasManuallyModified)
                .build();
    }
}
