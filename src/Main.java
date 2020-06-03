import org.junit.Test;
import org.omg.CORBA.TIMEOUT;

import java.awt.event.WindowFocusListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class Main {

    public static class Task implements Callable<String> {
        int time;
        String string;
        int id;

        public Task(int i) {
            this.time = i;
        }

        public Task(int i, String string) {
            this.time = i;
            this.string = string;
        }

        public Task(int i, int id) {
            this.time = i;
            this.id = id;
        }


        @Override
        public String call() throws InterruptedException {
            Integer threadId = Integer.valueOf(Thread.currentThread().getId() + "") - 12;
            String tid = "task : " + id + "  thread : " + threadId;
            TimeUnit.SECONDS.sleep(time);
            if (string != null) {
                return string;
            }
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
        Task task = new Task(5);
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
        assert !future.isDone();
        boolean after = future.isDone();
        while (true) {
            if (future.isDone()) {
                break;
            }
        }
        assert future.isDone();
    }

    @Test
    public void testExcutors() throws TimeoutException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(5));
        String s = future.get(20);
        assert "thread : 12".equals(s);
        assert future.isDone();
    }

    @Test
    public void testCancel() throws TimeoutException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(5));
        assert !future.isCancelled();
        future.cancel();
        assert null == future.get(10);
        assert future.isCancelled();
    }

    @Test
    public void testGet() {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(5, "apple"));
        String s = future.get();
        assert "apple".equals(s);
        assert future.isDone();
    }


    @Test
    public void testGetOutTime() throws TimeoutException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        IFuture<String> future = service.submit(new Task(5));
        try {
            String s = future.get(1);
        } catch (TimeoutException e) {
            assert "timeOut".equals(e.getMessage());
        }
    }

    // as we see,  there only have 5 worker do works when we submit 20 task.
    // and the task will be add in the task queue to wait.
    @Test
    public void workerCountLimits() throws TimeoutException {
        IExecutorService<String> service = IExecutors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            IFuture<String> future = service.submit(new Task(0, i));
            System.out.println(future.get());
        }
    }
}
