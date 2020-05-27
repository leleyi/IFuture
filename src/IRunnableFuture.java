/**
 *
 */
public interface IRunnableFuture<V> extends Runnable, IFuture {
    @Override
    void run();
}
