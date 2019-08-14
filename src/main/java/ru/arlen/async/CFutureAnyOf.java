package ru.arlen.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CFutureAnyOf {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CFutureAnyOf instance = new CFutureAnyOf();
        instance.testAnyOf();
    }

    private void testAnyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowInc)
                .thenApply(Utils::slowInc);
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowInc);
        CompletableFuture<?> winner = CompletableFuture.anyOf(future1, future2);
        System.out.println("result: " + winner.get());
    }
}
