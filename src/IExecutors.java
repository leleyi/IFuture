import java.util.concurrent.LinkedBlockingQueue;

class IExecutors {


    static IThreadPoolExecutor newFixedThreadPool(int nThreads) {

        return new IThreadPoolExecutor(nThreads);

    }
}