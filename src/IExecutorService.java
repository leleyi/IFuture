import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;

/**
 * @param <T>
 * @author author
 */
public abstract class IExecutorService<T> {

    IFuture<T> submit(Callable<T> task) {
        if (task == null) {
            throw new NullPointerException();
        }
        IFutureTask<T> ftask = new IFutureTask<>(task);
        execute(ftask);
        return ftask;
    }

    /**
     * temp
     *
     * @param ftask
     * @param <T>
     */
    abstract <T> void execute(Runnable ftask);

    /**
     * @return
     */
    abstract void shutdown();
}
