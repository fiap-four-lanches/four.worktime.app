package com.fiap.techchallenge.fourworktimeapp.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.fiap.techchallenge.fourworktimeapp.util.EntityBuilder.buildClock;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TimesheetDailyEntryTest {

    @Test
    public void shouldGetCorrectResultForEvenShift() {
        TimesheetDailyEntry dailyEntry = TimesheetDailyEntry.builder()
                .dailyClocks(Arrays.asList(
                        buildClock(2024, 03, 20, 8, 0, ClockType.IN),
                        buildClock(2024, 03, 20, 12, 0, ClockType.OUT)
                ))
                .build();

        int expectedMinutes = dailyEntry.getWorkedHoursInMinutes();
        assertThat(expectedMinutes).isEqualTo(240);
    }

    @Test
    public void shouldGetCorrectResultForOddShift() {
        TimesheetDailyEntry dailyEntry = TimesheetDailyEntry.builder()
                .dailyClocks(Arrays.asList(
                        buildClock(2024, 03, 20, 8, 10, ClockType.IN),
                        buildClock(2024, 03, 20, 11, 50, ClockType.OUT),
                        buildClock(2024, 03, 20, 13, 0, ClockType.IN),
                        buildClock(2024, 03, 20, 17, 0, ClockType.OUT)
                ))
                .build();

        int expectedMinutes = dailyEntry.getWorkedHoursInMinutes();
        assertThat(expectedMinutes).isEqualTo(460);
    }

}