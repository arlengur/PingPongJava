package ru.arlen.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CFutureThenAsync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CFutureThenAsync instance = new CFutureThenAsync();
        instance.promiseTestNext();
    }

    private void promiseTestNext() throws ExecutionException, InterruptedException {
        // supplyAsync создает новый поток из пула потоков
        CompletableFuture<Integer> init = CompletableFuture
                .supplyAsync(Utils::slowInt);
        CompletableFuture<Integer> future1 = init
                // thenApplyAsync выполняется в новом потоке
                .thenApplyAsync(Utils::slowInc);
        CompletableFuture<Integer> future2 = init
                .thenApplyAsync(Utils::slowInc);

        CompletableFuture<Integer> future3 = future1
                .thenCombine(future2, (x, y) -> x + y);
        System.out.println("result: " + future3.get());
    }
}
