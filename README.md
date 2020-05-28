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