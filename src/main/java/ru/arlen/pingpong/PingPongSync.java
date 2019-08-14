package ru.arlen.pingpong;

import java.util.concurrent.atomic.AtomicInteger;

import static ru.arlen.pingpong.Utils.await;
import static ru.arlen.pingpong.Utils.sleep;

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
                await(this);
            } else {
                System.out.println(msg);
                counter.decrementAndGet();
                last = msg;
                sleep();
                notify();
            }
        }
    }
}
