package com.fiap.techchallenge.fourworktimeapp.domain.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TimesheetUseCase {

    List<TimesheetDailyEntry> viewTimesheet(Long employeeId);
}
