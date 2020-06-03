
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IThreadPoolExecutor<T> extends IExecutorService {


    @Override
    public synchronized void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        if (coreSize < poolSize) {
            addThread(runnable);
        } else {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Worker implements Runnable {

        public Worker(Runnable runnable) {
            queue.offer(runnable);
        }

        @Override
        public void run() {
            while (true && RUNNING) {
                if (shutdown == true) {
                    Thread.interrupted();
                }
                Runnable task = null;
                try {
                    task = getTask();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized Runnable getTask() throws InterruptedException {
            return queue.take();
        }

        public void interruptIfIdle() {
            for (Thread thread : threadList) {
                System.out.println(thread.getName() + " interrupt");
                thread.interrupt();
            }
        }
    }

    private volatile boolean RUNNING = true;

    // task all in this queue

    private static BlockingQueue<Runnable> queue = null;

    private final HashSet<Worker> workers = new HashSet<Worker>();

    private final List<Thread> threadList = new ArrayList<Thread>();

    /**
     * size of pool
     */
    int poolSize = 0;

    /**
     * core thread num
     */
    volatile int coreSize = 0;

    boolean shutdown = false;

    public IThreadPoolExecutor(int poolSize) {
        this.poolSize = poolSize;
        queue = new LinkedBlockingQueue<Runnable>(poolSize);
    }


    public void addThread(Runnable runnable) {
        coreSize++;
        Worker worker = new Worker(runnable);
        workers.add(worker);
        Thread t = new Thread(worker);
        threadList.add(t);
        try {
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void shutdown() {
        RUNNING = false;
        if (!workers.isEmpty()) {
            for (Worker worker : workers) {
                worker.interruptIfIdle();
            }
        }
        shutdown = true;
        Thread.currentThread().interrupt();
    }

}
