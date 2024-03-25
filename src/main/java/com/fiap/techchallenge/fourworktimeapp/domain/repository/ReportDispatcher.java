package com.fiap.techchallenge.fourworktimeapp.domain.repository;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportDispatcher {

    void sendLastTimesheet(List<TimesheetDailyEntry> reportEntries, String requesterEmail);

}
