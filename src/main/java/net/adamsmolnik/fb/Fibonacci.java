package net.adamsmolnik.fb;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @see RecursiveTask
 *
 */
public class Fibonacci extends RecursiveTask<Long> {

    private static final long serialVersionUID = 307530666924178596L;

    private static final AtomicLong activeForksCount = new AtomicLong();

    private final long n;

    public Fibonacci(long n) {
        this.n = n;
        activeForksCount.incrementAndGet();
    }

    public Long compute() {
        if (n <= 1) {
            activeForksCount.decrementAndGet();
            return n;
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();

        Fibonacci f2 = new Fibonacci(n - 2);
        Long res = f2.compute() + f1.join();
        activeForksCount.decrementAndGet();
        return res;
    }

    public static long getActiveForksCount() {
        return activeForksCount.get();
    }
}