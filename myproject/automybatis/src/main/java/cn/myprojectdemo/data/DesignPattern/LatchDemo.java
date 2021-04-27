package cn.myprojectdemo.data.DesignPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author Worm
 * @Date 2020/10/6 10:38
 * @Version 1.0
 **/

public class LatchDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * 下面的程序使用的future对每个worker工作的时间进行统计，最后通过latch的countDown到0，
         * 通知boss，boss通过future传递的值能够知道每个worker的工作时间。
         * 这里有一点需要注意，在给boss传递参数的时候，可以让boss直接获得future中的值，但是如果使用
         * 这种方式，就没有必要使用latch了，因为在每个worker的值时需要使用future.get()，能够创建完成
         * 参数的时候，worker线程应该已经结束了。所以就没有必要使用latch了。
         *
         * 如果像下面程序传递的是future，然后在boss的线程中对future进行取值，就是需要latch的。因为在boss线程开
         * 开始的时候future没有执行完成，需要latch最后countDown到0，才能保证所有的future中都有结果了。
         */

        CountDownLatch downLatch = new CountDownLatch(4);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<Integer> future = executor.submit(new WorkerWithResult(downLatch, 1));

        Future<Integer> future1 = executor.submit(new WorkerWithResult(downLatch, 2));

        Future<Integer> future2 = executor.submit(new WorkerWithResult(downLatch, 3));
        Future<Integer> future4 = executor.submit(new WorkerWithResult(downLatch, 4));

        //FutureTask<Integer> futureTask = new FutureTask<>(new WorkerWithResult(downLatch, 4));

       // executor.submit(futureTask);

        List<Future> workTimeList = new ArrayList<>();

        workTimeList.add(future);

        workTimeList.add(future1);

        workTimeList.add(future2);
        workTimeList.add(future4);
        executor.shutdown();
        downLatch.await();
       // workTimeList.add(futureTask);
        for (Future<Integer> workTimeFuture : workTimeList) {

            System.out.println("work time is :" + workTimeFuture.get());

        }

       // executor.submit(new BossWithResult(downLatch, workTimeList));

       // executor.shutdown();

    }
}
