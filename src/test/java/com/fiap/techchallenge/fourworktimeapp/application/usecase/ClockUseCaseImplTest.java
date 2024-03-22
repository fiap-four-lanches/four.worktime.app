package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ClockRepository;
import com.fiap.techchallenge.fourworktimeapp.domain.valueobject.ClockEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClockUseCaseImplTest {

    @Mock
    private ClockRepository clockRepository;

    @InjectMocks
    private ClockUseCaseImpl clockUseCase;


    @Test
    public void shouldClockInWhenDataIsNew() {
        // Arrange
        var clockTime = LocalDateTime.now();
        var clockEntry = new ClockEntry(1L, clockTime, false);
        var dummyClock = clockEntry.toClock();


        // Act
        when(clockRepository.getLastClockForEmployee(any())).thenReturn(Optional.empty());
        dummyClock.setClockType(ClockType.IN);
        when(clockRepository.clockInOrClockOut(any())).thenReturn(dummyClock);
        var result = clockUseCase.clockInOrClockOut(clockEntry);

        // Assert
        assertEquals(ClockType.IN, result.getClockType());
    }

    @Test
    public void shouldClockOutWhenLastClockIsClockIn() {
        // Arrange
        var clockTime = LocalDateTime.now();
        var clockEntry = new ClockEntry(1L, clockTime, false);
        var dummyClock = clockEntry.toClock();
        var lastClock = Clock.builder().clockType(ClockType.IN).build();


        // Act
        when(clockRepository.getLastClockForEmployee(any())).thenReturn(Optional.of(lastClock));
        dummyClock.setClockType(ClockType.OUT);
        when(clockRepository.clockInOrClockOut(any())).thenReturn(dummyClock);
        var result = clockUseCase.clockInOrClockOut(clockEntry);

        // Assert
        assertEquals(ClockType.OUT, result.getClockType());
    }

    @Test
    public void shouldClockInWhenLastClockIsClockOut() {
        // Arrange
        var clockTime = LocalDateTime.now();
        var clockEntry = new ClockEntry(1L, clockTime, false);
        var dummyClock = clockEntry.toClock();
        var lastClock = Clock.builder().clockType(ClockType.OUT).build();

        when(clockRepository.getLastClockForEmployee(any())).thenReturn(Optional.of(lastClock));
        when(clockRepository.clockInOrClockOut(any())).thenReturn(dummyClock);

        // Act
        when(clockRepository.getLastClockForEmployee(any())).thenReturn(Optional.of(lastClock));
        dummyClock.setClockType(ClockType.IN);
        when(clockRepository.clockInOrClockOut(any())).thenReturn(dummyClock);
        var result = clockUseCase.clockInOrClockOut(clockEntry);

        // Assert
        assertEquals(ClockType.IN, result.getClockType());
    }
}