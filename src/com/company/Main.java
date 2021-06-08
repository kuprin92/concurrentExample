package com.company;

import java.util.UUID;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Port port = new Port(100);
        CountDownLatch countDownLatch = new CountDownLatch(5000);
        ExecutorService executorService = Executors.newFixedThreadPool(50);

        for (int i = 0; i < 5000; i++) {
            executorService.execute(() -> port.addShip(new Ship(100), countDownLatch));
        }
        executorService.shutdown();
        countDownLatch.await();
        int sum = port.getDocs().stream().peek(e -> System.out.println(e.getShips().size())).map(e -> e.getShips().size()).mapToInt(e -> e).sum();
        System.out.println("final "+sum);
    }
}

class Quest extends Thread {
    Quest() {
    }

    Quest(Runnable r) {
        super(r);
    }

    public void run() {
        System.out.print("thread ");
    }

}

class WalkThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread " + Thread.currentThread().getName() + " " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class InThread implements Runnable {
    public void run() {
        System.out.println("running...");
    }
}

class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        return UUID.randomUUID().toString();
    }
}

class Test {
    public synchronized String getName() throws InterruptedException {
        System.out.println("prepare data for getName");
        this.wait();
        return "data for getName" + UUID.randomUUID();
    }

    public synchronized String getLastName() throws InterruptedException {
        System.out.println("prepare data for getLastName");
        this.wait();
        return "data for getLastName" + UUID.randomUUID();
    }

    public synchronized void complete() throws InterruptedException {
        System.out.println("prepare complete");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("notify");
        this.notify();
        System.out.println("end notify");
    }
}