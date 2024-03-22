package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.entity;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Clock;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "clocks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClockJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "clocked_time")
    private LocalDateTime clockedTime;

    @Column(name = "clock_type")
    private ClockType clockType;

    @Column(name = "was_manually_modified")
    private boolean wasManuallyModified;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Clock toClock() {
        return Clock.builder()
                .id(this.id)
                .employeeId(this.employeeId)
                .clockedTime(this.clockedTime)
                .clockType(this.clockType)
                .wasManuallyModified(this.wasManuallyModified)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static ClockJpaEntity fromClock(Clock clock) {
        var jpaEntity = ClockJpaEntity.builder()
                .employeeId(clock.getEmployeeId())
                .clockedTime(clock.getClockedTime())
                .clockType(clock.getClockType())
                .wasManuallyModified(clock.isWasManuallyModified())
                .createdAt(clock.getCreatedAt())
                .updatedAt(clock.getUpdatedAt())
                .build();

        if (clock.getId() != null) {
            jpaEntity.setId(clock.getId());
        }
        return jpaEntity;
    }
}
