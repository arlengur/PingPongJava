package ru.arlen.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static ru.arlen.async.Utils.*;

public class CFutureCompose {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CFutureCompose instance = new CFutureCompose();
        instance.promiseTestNext1();
        instance.promiseTestNext2();
    }

    private void promiseTestNext1() throws ExecutionException, InterruptedException {
        // supplyAsync создает новый поток из пула потоков
        CompletableFuture<Void> res = getSlowInt()
                .thenCompose(Utils::getSlowInc)
                .thenAccept(System.out::println);
        res.get();
    }

    private void promiseTestNext2() throws ExecutionException, InterruptedException {
        // supplyAsync создает новый поток из пула потоков
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(Utils::slowInt)
                // thenApply переиспользует старый поток
                .thenApply(Utils::slowInc);
        CompletableFuture<Integer> future2 = future1
                .thenCompose(res -> CompletableFuture.supplyAsync(()->res))
                .thenApply(Utils::slowInc);
        System.out.println(future2.get());
    }
}
