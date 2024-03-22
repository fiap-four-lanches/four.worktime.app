package com.fiap.techchallenge.fourworktimeapp.domain.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.valueobject.ClockEntry;
import org.springframework.stereotype.Service;

@Service
public interface ClockUseCase {
    Clock clockInOrClockOut(ClockEntry entry);
}
