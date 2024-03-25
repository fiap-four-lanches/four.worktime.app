package com.fiap.techchallenge.fourworktimeapp.domain.repository;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClockRepository {
    Clock clockInOrClockOut(Clock entry);

    Optional<Clock> getLastClockForEmployee(Long employeeId);

    List<Clock> getAllClocksFromCurrentMonthForEmployee(Long employeeId);

    List<Clock> getAllLastMonthClocksForEmployee(Long employeeId);
}
