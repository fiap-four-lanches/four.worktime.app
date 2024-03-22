package com.fiap.techchallenge.fourworktimeapp.domain.repository;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClockRepository {
    Clock clockInOrClockOut(Clock entry);

    Optional<Clock> getLastClockForEmployee(Long employeeId);
}
