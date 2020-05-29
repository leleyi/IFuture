## Computer Process Organization

### lab2 - Computational process management

#### basic info

* **laboratory work number** : PLMM
* **variant description** :  Futures with worker pool
* **list of group members** : Liu Jun ; Wang Xin Yue

#### contribution

* contribution summary for each group member (should be checkable by git log and git blame);
  - Liu Jun Complete Thread Pool Executor and related interfaces and test
  - Wang Xin Yue Complete FutureTask and related interfaces
  - Together to  Complete readme.md

#### explanation 

Developing concurrent applications is hard. Today widely spread approach to do this is the future – a high-level interface for asynchronously executing functions or methods. As an example, you can see concurrent.futures in standard Python library.

In this variant, you should implement your library for futures.

**Requirements:**

1. All futures should be executed in a global worker pool.

2. The execution strategy:

   - execute futures in a results request sequence (if results already requested);
   - execute futures in a submission sequence (if results are not requested yet).
3. Futures should provide the following API:
   - IsDone() – return True if future evaluation is complete; 
   - InProgress() – return True if future evaluated right now;
   - Result(timeout=None)
     - return the future execution result (if the future is done); 
     - raise the exception (if the future is done and raise an exception); 
     - block until future is done (if the timeout is None and future is not done);
     - raise TimeoutError after timeout (if the timeout is not None and the future is not done). 
    - Cancel() – cancel a future (if the future not executed).
4. To proof correctness, you should use unit tests.

5. To proof a concurrent execution and execution strategy, you should demonstrate it on examples. If you can check it by automatic unit tests

– it will be preferred.

#### work demonstration 

```IFuture``` is usually used in conjunction with the thread pool to obtain the return value after the thread pool returns to execution. We assume that the ```IExecutors``` factory method is used to construct a thread pool es. There are two ways for es to perform a task. One is to execute ```es.execute (runnable)```, in which case there is no return value; the other is to execute ```es .submit (runnale) ```or ```es.submit (callable)```, this case will return a Future object, and then call Future's ```get ()``` to get the return value.

```java
public interface IFuture<V> {

    boolean isDone();
    boolean isCancelled();
    boolean cancel();

    V get();
    V get(long timeout) throws TimeoutException;
}
```

- ```IFuture``` is an interface. 
  - By calling Future's ```get ()``` method, a result value can be returned when the task is over. If the work is not over, the current thread will be blocked until the task is completed. 
  - For tasks, if the task has been stopped, the ```cancel ()``` method will return true; if the task has completed or has been stopped or the task cannot be stopped, then ```cancel ()``` will return a false. When a task is successfully stopped, he cannot perform it again. 
  - The ```isDone ()``` and```isCancel ()`` methods can determine whether the current job is completed and cancelled.

```java
public class IFutureTask<V> implements IRunnableFuture<V>
```

- ```IFutureTask``` implements the ```IRunnableFuture``` interface

```java
public class IThreadPoolExecutor<T> extends IExecutorService {


    @Override
    public synchronized void execute(Runnable runnable) {...}

    class Worker implements Runnable {...}

    private volatile boolean RUNNING = true;

    private static BlockingQueue<Runnable> queue = null;
    private final HashSet<Worker> workers = new HashSet<Worker>();
    private final List<Thread> threadList = new ArrayList<Thread>();

    int poolSize = 0;
    volatile int coreSize = 0;
    boolean shutdown = false;

    public IThreadPoolExecutor(int poolSize) {...}

    public void addThread(Runnable runnable) {...}

    @Override
    public void shutdown() {...}

}
```

- 
- work demonstration (how to use developed software, how to test it), should be repeatable by an instructor by given command-line
  examples;