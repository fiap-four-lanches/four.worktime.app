package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.ClockJpaRepository;
import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.ClockJpaEntity;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ClockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ClockRepositoryImpl implements ClockRepository {

    private ClockJpaRepository jpaRepository;

    @Override
    public Clock clockInOrClockOut(Clock entry) {
        var entity = ClockJpaEntity.fromClock(entry);
        var entrySaved = jpaRepository.save(entity);
        return entrySaved.toClock();
    }

    @Override
    public Optional<Clock> getLastClockForEmployee(Long employeeId) {
        var foundJpaEntity = jpaRepository.getClockJpaEntitiesByEmployeeIdOrderByClockedTimeDescLimit1(employeeId);
        return foundJpaEntity.map(ClockJpaEntity::toClock);
    }

    @Override
    public List<Clock> getAllClocksFromCurrentMonthForEmployee(Long employeeId) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime startOfMonth = LocalDateTime.of(current.getYear(), current.getMonthValue(), 1, 0, 0);

        List<ClockJpaEntity> foundJpaEntity = jpaRepository
                .findByEmployeeIdAndClockedTimeAfter(employeeId, startOfMonth);
        return foundJpaEntity.stream().map(ClockJpaEntity::toClock).collect(Collectors.toList());
    }
}
