package com.fiap.techchallenge.fourworktimeapp.application.dto;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TimesheetDailyEntryDTO {

    private LocalDate date;
    private List<Clock> entries;
    private int workedMinutes;

    public static TimesheetDailyEntryDTO fromTimesheetEntry(TimesheetDailyEntry timesheetDailyEntry) {
        return TimesheetDailyEntryDTO.builder()
                .date(timesheetDailyEntry.getDate())
                .entries(timesheetDailyEntry.getDailyClocks())
                .workedMinutes(timesheetDailyEntry.getWorkedHoursInMinutes())
                .build();
    }
}
