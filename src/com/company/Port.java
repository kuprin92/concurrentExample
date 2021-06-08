package com.company;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Port {
    private final Set<Doc> docs;

    public Port(int size) {
        docs = new HashSet<>(size);
        IntStream.range(0, size).forEach(e -> {
            docs.add(new Doc());
        });
    }

    public Set<Doc> getDocs() {
        return docs;
    }

    @Override
    public String toString() {
        return "Port{" +
                "docs=" + docs +
                '}';
    }

    public void addShip(Ship ship, CountDownLatch countDownLatch) {
        while (true) {
            for (Doc doc : docs) {
                try {
                    if (doc.tryLock()) {
                        doc.addShip(ship);
                        countDownLatch.countDown();
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    countDownLatch.countDown();
                    return;
                }
            }
        }
    }
}
