package ru.arlen.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CFutureCombine {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CFutureCombine instance = new CFutureCombine();
        instance.promiseTestNext();
    }

    private void promiseTestNext() throws ExecutionException, InterruptedException {
        // supplyAsync создает новый поток из пула потоков
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowInc);
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowInc);
        CompletableFuture<?> future3 = future1.thenCombine(future2, (x, y) -> x + y);
        System.out.println("result: " + future3.get());
    }
}
