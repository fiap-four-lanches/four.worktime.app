package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.ClockJpaRepository;
import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.ClockJpaEntity;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ClockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
