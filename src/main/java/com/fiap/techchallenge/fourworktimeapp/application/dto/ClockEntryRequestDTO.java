package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.techchallenge.fourworktimeapp.domain.valueobject.ClockEntry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClockEntryRequestDTO extends ClockEntry {
    public ClockEntryRequestDTO(String registry, Long employeeId, LocalDateTime clockedTime, boolean wasManuallyModified) {
        super(registry, employeeId, clockedTime, wasManuallyModified);
    }

    public ClockEntry toEntry() {
        return ClockEntry.builder()
                .registry(this.getRegistry())
                .employeeId(this.getEmployeeId())
                .clockedTime(this.getClockedTime())
                .wasManuallyModified(this.isWasManuallyModified())
                .build();
    }
}
