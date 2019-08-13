package ru.arlen;

import java.util.concurrent.atomic.AtomicInteger;

import static ru.arlen.Utils.sleep;

public class PingPongAtomic {
    private static final String MSGS[] = {"ping", "pong"};
    private final AtomicInteger lastIdx = new AtomicInteger(1);
    private final AtomicInteger counter = new AtomicInteger(1_000_000);

    public PingPongAtomic() {
        new Thread(() -> this.action(1, 0)).start();
        new Thread(() -> this.action(0, 1)).start();
    }

    public static void main(String[] args) {
        new PingPongAtomic();
    }

    public void action(int msgIdxLast, int msgIdxNew) {
        while (counter.get() > 0) {
            if (lastIdx.compareAndSet(msgIdxLast, msgIdxNew)) {
                System.out.println(MSGS[msgIdxNew]);
                counter.decrementAndGet();
                sleep();
            }
        }
    }
}
