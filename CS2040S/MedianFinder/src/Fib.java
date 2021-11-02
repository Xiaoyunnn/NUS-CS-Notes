import java.util.concurrent.RecursiveTask;
import java.time.Instant;
import java.time.Duration;

class Fib extends RecursiveTask<Integer> {
    final int n;
    Fib(int n) {
        this.n = n;
    }
    private void waitOneSec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) { }
    }
    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread().getName() + " : " + n);
        waitOneSec();
        if (n <= 1) {
            return n;
        }
        Fib f1 = new Fib(n - 1);
        Fib f2 = new Fib(n - 2);

// try different variants here...
        f1.fork();

        return f2.compute() + f1.join();
    }
    public static void main(String[] args) {
        Instant start = Instant.now();
        System.out.println(new Fib(15).compute());
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }
}