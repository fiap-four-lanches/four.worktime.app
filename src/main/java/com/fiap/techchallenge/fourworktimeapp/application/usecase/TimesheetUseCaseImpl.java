package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ClockRepository;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ReportDispatcher;
import com.fiap.techchallenge.fourworktimeapp.domain.usecase.TimesheetUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimesheetUseCaseImpl implements TimesheetUseCase {

    private ClockRepository repository;
    private ReportDispatcher reportDispatcher;

    @Override
    public List<TimesheetDailyEntry> viewTimesheet(Long employeeId) {
        List<Clock> monthlyClocks = repository.getAllClocksFromCurrentMonthForEmployee(employeeId);

        return buildTimesheetDailyEntries(monthlyClocks);
    }

    @Override
    public void sendLastTimesheet(Long employeeId, String requesterEmail) {
        List<Clock> lastMonthClocks = repository.getAllLastMonthClocksForEmployee(employeeId);
        reportDispatcher.sendLastTimesheet(buildTimesheetDailyEntries(lastMonthClocks), requesterEmail);
    }

    private static List<TimesheetDailyEntry> buildTimesheetDailyEntries(List<Clock> monthlyClocks) {
        Map<LocalDate, List<Clock>> clocksByDate = monthlyClocks.stream()
                .collect(Collectors.groupingBy(clock -> clock.getClockedTime().toLocalDate()));

        return clocksByDate.keySet().stream()
                .map(date -> {
                    List<Clock> clock = clocksByDate.get(date);
                    return TimesheetDailyEntry.builder()
                            .date(date)
                            .dailyClocks(clock).build();
                }).collect(Collectors.toList());
    }

}
