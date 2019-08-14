package ru.arlen.async;

import java.util.concurrent.CompletableFuture;

import static ru.arlen.pingpong.Utils.sleep;

public class CFuturePipe {
    public static void main(String[] args) {
        CFuturePipe instance = new CFuturePipe();
        instance.promiseTestNext();
    }

    private void promiseTestNext() {
        // supplyAsync создает новый поток из пула потоков
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(Utils::slowInt)
                .thenApply(Utils::slowInc)
                .thenApply(Utils::slowInc)
                .thenAccept(res -> System.out.println("finished " + res))
                .thenRun(() -> System.out.println("look at result"));
        System.out.println("promiseTestNext is finished");
        sleep();
        sleep();
        sleep();
    }
}
