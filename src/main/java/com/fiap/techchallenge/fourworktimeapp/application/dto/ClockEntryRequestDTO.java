package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fiap.techchallenge.fourworktimeapp.domain.valueobject.ClockEntry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClockEntryRequestDTO extends ClockEntry {
    public ClockEntryRequestDTO(Long employeeId, LocalDateTime clockedTime, boolean wasManuallyModified) {
        super(employeeId, clockedTime, wasManuallyModified);
    }

    public ClockEntry toEntry() {
        return ClockEntry.builder()
                .employeeId(this.getEmployeeId())
                .clockedTime(this.getClockedTime())
                .wasManuallyModified(this.isWasManuallyModified())
                .build();
    }
}
