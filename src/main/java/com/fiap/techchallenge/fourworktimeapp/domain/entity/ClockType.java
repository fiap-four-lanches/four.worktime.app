package com.fiap.techchallenge.fourworktimeapp.domain.entity;

import java.util.Objects;

public enum ClockType {
    IN("in"),
    OUT("out");
    private final String value;

    ClockType(String value) {
        this.value = value;
    }

    public boolean isClockIn() {
        return Objects.equals(this.value, IN.value);
    }

    public boolean isClockOut() {
        return Objects.equals(this.value, OUT.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
