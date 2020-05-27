import com.sun.tracing.dtrace.ArgsAttributes;
import org.junit.Test;
import sun.awt.windows.WPrinterJob;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
//import java.util.concurrent.Future;
//import java.util.concurrent.ThreadFactory;

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
            String tid = String.valueOf(Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(time);
            return tid;
        }
    }

    public static void main(String[] args) throws Exception {


//        for (int i = 0; i < 1; i++) {
//            IExecutorService<Future<String>> es = new IExecutorService<>();
//            IFuture<Future<String>> o = es.submit(new Task());
//            results.add(o);
//        }
//
//        for (IFuture<Future<String>> res : results) {
//            System.out.println(res.get());
//        }
    }

    @Test
    public void testMuiTask() throws TimeoutException {
        List<IFuture> results = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            IExecutorService<String> service = IExecutors.newFixedThreadPool(15);
            IFuture<String> future = service.submit(new Task(1));
            results.add(future);
        }
        //
        System.out.println("sss : "+ results.size());
        for (IFuture res :
                results) {
            System.out.println(res.get());
        }
    }


    @Test
    public void testIsDone() throws InterruptedException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(10));
        boolean before = future.isDone();
        System.out.println("isDone: " + before);
        TimeUnit.SECONDS.sleep(5);
        boolean after = future.isDone();
        System.out.println("isDone: " + after);
    }

    @Test
    public void testExcutors() throws TimeoutException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(100));
        String s = future.get(600000000);
        System.out.println(s);
    }

    @Test
    public void testCancel() {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(10000));
        future.cancel();
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
