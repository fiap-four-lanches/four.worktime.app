package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.ClockJpaRepository;
import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.ClockJpaEntity;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClockRepositoryImplTest {

    @Mock
    private ClockJpaRepository jpaRepository;

    @InjectMocks
    private ClockRepositoryImpl clockRepository;

    @Test
    void shouldClockInSuccessfully() {
        // Arrange
        var ts = LocalDateTime.now();
        var sampleClock = generateSampleClock(ts);
        var expectedClockJPAEntity = ClockJpaEntity.fromClock(sampleClock);
        expectedClockJPAEntity.setId(1L);
        expectedClockJPAEntity.setCreatedAt(ts);
        expectedClockJPAEntity.setUpdatedAt(ts);

        // Act
        when(jpaRepository.save(any())).thenReturn(expectedClockJPAEntity);
        var result = clockRepository.clockInOrClockOut(sampleClock);

        // Assert
        assertEquals(expectedClockJPAEntity.toClock(), result);
    }

    @Test
    public void shouldGetLastTimeClockedForEmployee() {
        // Arrange
        var employeeId = 1L;
        var ts = LocalDateTime.now();
        var expectedClock = generateSampleClock(ts);
        var expectedJpaEntity = ClockJpaEntity.fromClock(expectedClock);
        when(jpaRepository.getClockJpaEntitiesByEmployeeIdOrderByClockedTimeDescLimit1(employeeId))
                .thenReturn(Optional.of(expectedJpaEntity));

        // Act
        Optional<Clock> actualClock = clockRepository.getLastClockForEmployee(employeeId);

        // Assert
        assertTrue(actualClock.isPresent(), "Expected a non-empty Optional<Clock>");
        assertEquals(expectedClock, actualClock.get(), "Expected the returned Clock to match the mocked Clock object");
    }

    @Test
    public void shouldNotGetLastTimeClockedForEmployee() {
        // Arrange
        var employeeId = 1L;
        when(jpaRepository.getClockJpaEntitiesByEmployeeIdOrderByClockedTimeDescLimit1(employeeId))
                .thenReturn(Optional.empty());

        // Act
        Optional<Clock> actualClock = clockRepository.getLastClockForEmployee(employeeId);

        // Assert
        assertTrue(actualClock.isEmpty(), "Expected an empty Optional<Clock>");
    }

    // This method is to generate a sample clock
    private Clock generateSampleClock(LocalDateTime ts) {
        return Clock.builder()
                .employeeId(1L)
                .clockType(ClockType.IN)
                .clockedTime(ts)
                .build();
    }
}