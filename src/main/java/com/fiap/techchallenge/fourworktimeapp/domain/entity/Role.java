package com.fiap.techchallenge.fourworktimeapp.domain.entity;

public enum Role {
    WORKER("worker"),
    LEAD("lead"),
    MANAGER("manager");
    private final String value;

    Role(String value) {
        this.value = value;
    }

    public boolean hasLeadership() {
        return this.value.equals(LEAD.value) || this.value.equals(MANAGER.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
