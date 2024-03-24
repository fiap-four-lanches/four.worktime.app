package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ClockRepository;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.EmployeeRepository;
import com.fiap.techchallenge.fourworktimeapp.domain.usecase.ClockUseCase;
import com.fiap.techchallenge.fourworktimeapp.domain.valueobject.ClockEntry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@Service
public class ClockUseCaseImpl implements ClockUseCase {

    private ClockRepository repository;
    private EmployeeRepository employeeRepository;

    @Transactional
    @Override
    public Clock clockInOrClockOut(ClockEntry entry) {
        var clock = entry.toClock();
        var now = LocalDateTime.now();
        clock.setCreatedAt(now);
        clock.setUpdatedAt(now);

        if (entry.getEmployeeId() == null) {
            var employeeFound = employeeRepository.findEmployeeByRegistry(entry.getRegistry());
            entry.setEmployeeId(employeeFound.getId());
        }


        var lastClockFound = repository.getLastClockForEmployee(entry.getEmployeeId());

        if (lastClockFound.isEmpty()) {
            clock.setClockType(ClockType.IN);
            log.info("first clock in for employee [clock:{}]", clock);
            return repository.clockInOrClockOut(clock);
        }

        var lastClock = lastClockFound.get();

        if (lastClock.getClockType().isClockOut()) {
            log.info("clock in for employee [clock:{}]", clock);
            clock.setClockType(ClockType.IN);
        }

        if (lastClock.getClockType().isClockIn()) {
            log.info("clock out for employee [clock:{}]", clock);
            clock.setClockType(ClockType.OUT);
        }

        return repository.clockInOrClockOut(clock);
    }
}
