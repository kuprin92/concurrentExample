package com.company;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Doc {
    private final UUID id = UUID.randomUUID();
    private final HashSet<Ship> ships = new HashSet<>();
    private final ReentrantLock lock = new ReentrantLock();
    private boolean isAvailable = true;

    public HashSet<Ship> getShips() {
        return ships;
    }

    public boolean tryLock() throws InterruptedException {
        return lock.tryLock();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void addShip(Ship ship) {
        if (!lock.isLocked()) {
            throw new RuntimeException("is locked");
        }
        //System.out.println("add ship " + ship);
        try {
            if (!isAvailable) throw new IllegalArgumentException("is isAvailable");

            isAvailable = false;
            try {
                TimeUnit.MILLISECONDS.sleep(ship.getWaitTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            ships.add(ship);
            if (isAvailable) {
                throw new IllegalArgumentException("is isAvailable");
            }
            isAvailable = true;
        } finally {
//            System.out.println("end add ship " + ship);
            lock.unlock();
        }
    }

    public UUID getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doc doc = (Doc) o;
        return Objects.equals(id, doc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Doc{" +
                "id=" + id +
                ", ships=" + ships +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
