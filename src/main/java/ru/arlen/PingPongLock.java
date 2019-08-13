package ru.arlen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PingPongLock {
    private String last = "pong";
    private final AtomicInteger counter = new AtomicInteger(1_000_000);
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public PingPongLock() {
        new Thread(() -> this.action("ping")).start();
        new Thread(() -> this.action("pong")).start();
    }

    public static void main(String[] args) {
        new PingPongSync();
    }

    public void action(String msg) {
        while (counter.get() > 0) {
            lock.lock();
            try {
                if (msg.equals(last)) {
                    await(condition);
                } else {
                    System.out.println(msg);
                    counter.decrementAndGet();
                    last = msg;
                    sleep();
                    condition.signal();
                }
            } finally {
                lock.unlock();
            }

        }
    }

    public static void await(Condition condition) {
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

        public static void sleep() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}