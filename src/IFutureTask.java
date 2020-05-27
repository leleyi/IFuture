import org.omg.CORBA.TIMEOUT;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.Time;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author leleyi
 */
public class IFutureTask<V> implements IRunnableFuture<V> {

    private volatile int state;
    private static final int NEW = 0;
    private static final int DONE = 1;
    private static final int RUNNING = 2;
    private static final int CANCEL = 3;
    private Callable<V> callable;
    private V result;

    public IFutureTask(Callable<V> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        this.callable = callable;
        this.state = NEW;
    }

    @Override
    public void run() {
        state = RUNNING;
        try {
            V i = callable.call();
            result = i;
            state = DONE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean isDone() {
        return state == DONE;
    }

    @Override
    public boolean isCancelled() {
        return state == CANCEL;
    }

    @Override
    public boolean cancel() {
        Thread.currentThread().interrupt();
        state = CANCEL;
        return false;
    }

    @Override
    public synchronized Object get() {
        while (state != DONE) ;
        return this.result;
    }

    @Override
    public Object get(long timeout) throws TimeoutException {
        if (Thread.interrupted()) {
            return null;
        }
        if (timeout <= 0L) {
            throw new TimeoutException();
        }
        long l = TimeUnit.NANOSECONDS.toNanos(timeout);

        long d = System.nanoTime() + l;
        while (d > System.nanoTime()) {
            if (state == DONE) {
                return this.result;
            }
        }
        throw new TimeoutException();
    }
}
