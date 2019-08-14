package ru.arlen.pingpong;

import java.util.concurrent.locks.Condition;

public class Utils {
    public static void await(Condition condition) {
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void await(Object obj) {
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
