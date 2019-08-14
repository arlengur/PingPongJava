package ru.arlen.async;

import java.util.concurrent.CompletableFuture;

import static ru.arlen.pingpong.Utils.sleep;

public class Utils {
    public static CompletableFuture<Integer> getSlowInt() {
        sleep();
        return CompletableFuture.completedFuture(1);
    }

    public static CompletableFuture<Integer> getSlowInc(Integer i) {
        sleep();
        return CompletableFuture.completedFuture(1 + i);
    }

    public static int slowInt() {
        sleep();
        return 1;
    }

    public static int slowInc(Integer i) {
        sleep();
        return 1 + i;
    }

    public static int slowIncEx(Integer i) {
        throw new RuntimeException();
    }
}
