package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity.ClockJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClockJpaRepository extends JpaRepository<ClockJpaEntity, Long> {

    @Query("SELECT e FROM ClockJpaEntity e WHERE e.employeeId = ?1 ORDER BY e.clockedTime DESC LIMIT 1")
    Optional<ClockJpaEntity> getClockJpaEntitiesByEmployeeIdOrderByClockedTimeDescLimit1(Long employeeId);

    List<ClockJpaEntity> findByEmployeeIdAndClockedTimeAfter(Long employeeId, LocalDateTime clockedTime);

    List<ClockJpaEntity> findByEmployeeIdAndClockedTimeAfterAndClockedTimeBefore(Long employeeId, LocalDateTime startClockedTime, LocalDateTime endClockedTime);

}
