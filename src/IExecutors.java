import java.util.concurrent.LinkedBlockingQueue;

public class IExecutors {


    public static IThreadPoolExecutor newFixedThreadPool(int nThreads) {

        return new IThreadPoolExecutor(nThreads);

    }
}