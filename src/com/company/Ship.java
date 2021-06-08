package com.company;

import java.util.UUID;

public class Ship {
    private final UUID id = UUID.randomUUID();
    private int waitTime;

    public Ship(int waitTime) {
        this.waitTime = waitTime;
    }

    public UUID getId() {
        return id;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", waitTime=" + waitTime +
                '}';
    }
}
