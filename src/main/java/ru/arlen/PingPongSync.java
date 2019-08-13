package ru.arlen;

import java.util.concurrent.atomic.AtomicInteger;

public class PingPongSync {
    private String last = "pong";
    private final AtomicInteger counter = new AtomicInteger(1_000_000);

    public PingPongSync() {
        new Thread(() -> this.action("ping")).start();
        new Thread(() -> this.action("pong")).start();
    }

    public static void main(String[] args) {
        new PingPongSync();
    }

    public synchronized void action(String msg) {
        while (counter.get() > 0) {
            if (msg.equals(last)) {
                wait(this);
            } else {
                System.out.println(msg);
                counter.decrementAndGet();
                last = msg;
                sleep();
                notify();
            }
        }
    }

    public static void wait(Object obj) {
        try {
            obj.wait();
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
