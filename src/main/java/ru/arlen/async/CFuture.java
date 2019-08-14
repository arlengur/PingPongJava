package ru.arlen.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.arlen.pingpong.Utils.sleep;

public class CFuture {
    public static void main(String[] args) {
        CFuture instance = new CFuture();
        instance.promiseTestNext();
    }

    private void promiseTestNext() {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenAccept(res -> System.out.println("finished " + res))
                .thenRun(() -> System.out.println("look at result"));
        System.out.println("promiseTestNext is finished");
        sleep();
    }

    private void ownPullThread() {
        ExecutorService es = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(Utils::slowInt, es)
                .thenApplyAsync(Utils::slowInc);
    }

    // используется для колбеков
    private CompletableFuture<String> customCF() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            cf.completeExceptionally(e);
        }
        cf.complete("result");
        return cf;
    }
}
