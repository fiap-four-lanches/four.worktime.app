package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.ClockJpaRepository;
import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.ClockJpaEntity;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClockRepositoryImplTest {

    @Mock
    private ClockJpaRepository jpaRepository;

    @Captor
    private ArgumentCaptor<LocalDateTime> dateCaptor;

    @Captor
    private ArgumentCaptor<LocalDateTime> endDateCaptor;

    @Captor
    private ArgumentCaptor<LocalDateTime> startDateCaptor;

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


    @Test
    public void shouldReturnAllClocksForCurrentMonthByForEmployee() {
        // Arrange
        var employeeId = 1L;
        when(jpaRepository.findByEmployeeIdAndClockedTimeAfter(eq(employeeId), dateCaptor.capture()))
                .thenReturn(Collections.emptyList());

        // Act
        clockRepository.getAllClocksFromCurrentMonthForEmployee(employeeId);

        LocalDateTime expectedLocalDateTime = dateCaptor.getValue();
        // Assert
        assertThat(expectedLocalDateTime.getDayOfMonth()).isEqualTo(1);
        assertThat(expectedLocalDateTime.getMonthValue()).isEqualTo(LocalDateTime.now().getMonthValue());
        assertThat(expectedLocalDateTime.getYear()).isEqualTo(LocalDateTime.now().getYear());
    }

    @Test
    public void shouldReturnAllClocksForLasMonthByForEmployee() {
        // Arrange
        var employeeId = 1L;
        when(jpaRepository.findByEmployeeIdAndClockedTimeAfterAndClockedTimeBefore(eq(employeeId), startDateCaptor.capture(), endDateCaptor.capture()))
                .thenReturn(Collections.emptyList());

        // Act
        clockRepository.getAllLastMonthClocksForEmployee(employeeId);

        LocalDateTime expectedStartDate = startDateCaptor.getValue();
        LocalDateTime expectedEndDate = endDateCaptor.getValue();

        // Assert
        assertThat(expectedStartDate.getDayOfMonth()).isEqualTo(1);
        assertThat(expectedStartDate.getMonthValue()).isEqualTo(2);
        assertThat(expectedEndDate.getDayOfMonth()).isEqualTo(29);
        assertThat(expectedEndDate.getMonthValue()).isEqualTo(2);
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