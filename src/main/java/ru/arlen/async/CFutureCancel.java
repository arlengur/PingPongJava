package ru.arlen.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CFutureCancel {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CFutureCancel instance = new CFutureCancel();
        instance.testEx();
    }

    private void testEx() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(Utils::slowInt);
        future.cancel(true);
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
