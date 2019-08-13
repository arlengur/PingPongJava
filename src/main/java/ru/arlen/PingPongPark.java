package ru.arlen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

import static ru.arlen.Utils.sleep;

public class PingPongPark {
    private AtomicReference<String> last = new AtomicReference<>("pong");
    private AtomicReference<Thread> parkedThread = new AtomicReference<>();
    private final AtomicInteger counter = new AtomicInteger(1_000_000);

    public PingPongPark() {
        new Thread(() -> this.action("ping")).start();
        new Thread(() -> this.action("pong")).start();
    }

    public static void main(String[] args) {
        new PingPongSync();
//        static {
//            // reduce the risk of lost unpark due to classloading
//            Class<?> ensureLoaded = LockSupport.class;
//        }
    }

    public void action(String msg) {
        while (counter.get() > 0) {
            if (msg.equals(last.get())) {
                parkedThread.set(Thread.currentThread());
                LockSupport.park();
                if (Thread.interrupted())
                    System.out.println("Thread was interrupted.");
            } else {
                System.out.println(msg);
                counter.decrementAndGet();
                last.set(msg);
                sleep();
                if (parkedThread.get() != null)
                    LockSupport.unpark(parkedThread.get());
            }
        }
    }
}

