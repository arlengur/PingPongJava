package ru.arlen.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CFutureApplyToEither {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CFutureApplyToEither instance = new CFutureApplyToEither();
        instance.testApplyToEither();
    }

    private void testApplyToEither() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowInc)
                .thenApply(Utils::slowInc);
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowInc);
        // applyToEither применяет функцию к победителю
        CompletableFuture<?> winner = future1.applyToEither(future2, Utils::slowInc);
        System.out.println("result: " + winner.get());
    }
}
