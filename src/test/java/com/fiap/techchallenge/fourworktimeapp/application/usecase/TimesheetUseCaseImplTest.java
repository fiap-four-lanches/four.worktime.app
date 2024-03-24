package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ClockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimesheetUseCaseImplTest {

    @Mock
    private ClockRepository clockRepository;

    @InjectMocks
    private TimesheetUseCaseImpl useCase;

    @Test
    public void should() {
        when(clockRepository.getAllClocksFromCurrentMonthForEmployee(eq(1L)))
                .thenReturn(asList(
                        buildClock(2024, 03, 19, 8, 0, ClockType.IN),
                        buildClock(2024, 03, 19, 12, 0, ClockType.OUT),
                        buildClock(2024, 03, 20, 8, 10, ClockType.IN),
                        buildClock(2024, 03, 20, 11, 50, ClockType.OUT)
                ));

        List<TimesheetDailyEntry> expectedDailyEntries = useCase.viewTimesheet(1L);


        assertThat(getEntryByDay(expectedDailyEntries, 19).getWorkedHoursInMinutes()).isEqualTo(240);
        assertThat(getEntryByDay(expectedDailyEntries, 20).getWorkedHoursInMinutes()).isEqualTo(220);
    }

    private static Clock buildClock(int year, int month, int day, int hour, int minute, ClockType type) {
        return Clock.builder().clockedTime(LocalDateTime.of(year, month, day, hour, minute)).clockType(type).build();
    }

    private TimesheetDailyEntry getEntryByDay(List<TimesheetDailyEntry> dailyEntries, int day) {
        return dailyEntries.stream().filter(entry-> entry.getDate().getDayOfMonth() == day).findFirst().get();
    }
}