import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class Main {

    public static class Task implements Callable<String> {
        int time;

        public Task(int i) {
            this.time = i;
        }

        @Override
        public String call() throws InterruptedException {
            String tid = "thread : " + Thread.currentThread().getId();
            TimeUnit.SECONDS.sleep(time);
            return tid;
        }
    }

    @Test
    public void testMuiTask() throws TimeoutException {
        List<IFuture> results = new ArrayList<>();
        IExecutorService<String> service = IExecutors.newFixedThreadPool(15);
        for (int i = 0; i < 10; i++) {
            IFuture<String> future = service.submit(new Task(1));
            results.add(future);
        }
        //
        for (IFuture res :
                results) {
            System.out.println(res.get());
        }
    }


    @Test
    public void testIsDone() throws InterruptedException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(5));
        boolean before = future.isDone();
        System.out.println("isDone: " + before);
        boolean after = future.isDone();
        while (true) {
            if (future.isDone()) {
                System.out.println("finsh : "+ future.isDone());
                break;
            }
        }

    }

    @Test
    public void testExcutors() throws TimeoutException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(5));
        String s = future.get(20);
        System.out.println(s);
    }

    @Test
    public void testCancel() throws TimeoutException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(5));
        future.cancel();
        System.out.println(future.get(10));
        System.out.println(future.isCancelled());
    }

    @Test
    public void testGet() {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(10000));
        String s = future.get();
        System.out.println(s);
    }


    @Test
    public void testGetOutTime() throws TimeoutException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(10000));
        String s = future.get(10);
        System.out.println(s);
    }
}
