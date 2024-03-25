package com.fiap.techchallenge.fourworktimeapp.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TimesheetDailyEntry {

    private LocalDate date;
    private List<Clock> dailyClocks;

    public int getWorkedHoursInMinutes() {
        int workedMinutes = 0;
        Clock shiftStart = null;
        for(Clock clock: dailyClocks) {
            if(shiftStart == null && clock.getClockType().isClockIn()) {
                shiftStart = clock;
            } else if (shiftStart != null && clock.getClockType().isClockOut()) {
                workedMinutes += minuteInTime(clock.getClockedTime()) - minuteInTime(shiftStart.getClockedTime());
                shiftStart = null;
            }
        }
        return workedMinutes;
    }

    private int minuteInTime(LocalDateTime date) {
        return (date.getHour() * 60) + date.getMinute();
    }
}
