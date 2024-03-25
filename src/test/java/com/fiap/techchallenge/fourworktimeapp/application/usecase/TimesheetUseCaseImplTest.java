package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ClockRepository;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ReportDispatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.fiap.techchallenge.fourworktimeapp.util.EntityBuilder.buildClock;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimesheetUseCaseImplTest {

    @Mock
    private ClockRepository clockRepository;
    @Mock
    private ReportDispatcher reportDispatcher;

    @InjectMocks
    private TimesheetUseCaseImpl useCase;

    @Test
    public void shouldReturnExpectedDates() {
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

    @Test
    public void shouldSendLastClosedTimesheet() {
        when(clockRepository.getAllLastMonthClocksForEmployee(eq(1L))).thenReturn(Collections.emptyList());

        String requesterEmail = "lucasbz@gmail.com";
        useCase.sendLastTimesheet(1L, requesterEmail);

        verify(reportDispatcher).sendLastTimesheet(any(List.class), eq(requesterEmail));
    }

    private TimesheetDailyEntry getEntryByDay(List<TimesheetDailyEntry> dailyEntries, int day) {
        return dailyEntries.stream().filter(entry-> entry.getDate().getDayOfMonth() == day).findFirst().get();
    }
}