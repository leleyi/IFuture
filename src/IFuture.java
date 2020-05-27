import java.util.concurrent.TimeoutException;

/**
 * @author author
 */
public interface IFuture<V> {

    boolean isDone();

    boolean isCancelled();

    boolean cancel();

    V get();

    V get(long timeout) throws TimeoutException;
}
