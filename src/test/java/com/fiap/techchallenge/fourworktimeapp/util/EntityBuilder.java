package com.fiap.techchallenge.fourworktimeapp.util;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EntityBuilder {

    public static TimesheetDailyEntry buildDailyTimesheetEntry(int year, int month, int day, int firstShiftStartHour, int firstShiftEndHour) {
        return TimesheetDailyEntry.builder()
                .date(LocalDate.of(year, month, day))
                .dailyClocks(List.of(
                        buildClock(year, month, day, firstShiftStartHour, 0, ClockType.IN),
                        buildClock(year, month, day, firstShiftEndHour, 0, ClockType.OUT),
                        buildClock(year, month, day, 13, 0, ClockType.IN),
                        buildClock(year, month, day, 17, 0, ClockType.OUT)
                )).build();
    }

    public static Clock buildClock(int year, int month, int day, int hour, int minute, ClockType type) {
        return Clock.builder()
                .employeeId(1L).clockedTime(LocalDateTime.of(year, month, day, hour, minute)).clockType(type).build();
    }
}
