package ru.arlen.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static ru.arlen.pingpong.Utils.sleep;

public class CFutureExceptions {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CFutureExceptions instance = new CFutureExceptions();
        instance.testEx();
        instance.testHandle();
    }

    private void testEx() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowIncEx)
                .thenApply(Utils::slowInc)
                .exceptionally(ex -> {
                    System.out.println("Exception!");
                    return 0;
                })
                .thenApply(Utils::slowInc);
        // exceptionally вернул 0 потом slowInc добавил к ней 1
        System.out.println(future.get());
    }

    private void testHandle() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowInc)
                // выполняется в любом случае
                .handle((ok, ex) -> {
                    if (ex != null) System.out.println("Exception!");
                    return ok == null ? 0 : ok;
                })
                .thenApply(Utils::slowInc);
        // exceptionally вернул 0 потом slowInc добавил к ней 1
        System.out.println(future.get());
    }
}
