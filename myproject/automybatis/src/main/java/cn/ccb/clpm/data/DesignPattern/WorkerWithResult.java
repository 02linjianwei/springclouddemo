package cn.ccb.clpm.data.DesignPattern;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @Author Worm
 * @Date 2020/10/6 10:37
 * @Version 1.0
 **/

public class WorkerWithResult implements Callable<Integer> {

    private CountDownLatch downLatch;

    private int workId;


    public WorkerWithResult(CountDownLatch downLatch, int workId) {

        this.downLatch = downLatch;

        this.workId = workId;

    }


    @Override

    public Integer call() throws Exception {

        int randomSleepTime = -1;

        try {

            System.out.println(workId + " is working...");

            randomSleepTime = new Random().nextInt(3000);
//测试着取消睡眠
//            Thread.sleep(randomSleepTime);

            System.out.println(workId + " finish the job.");

        } finally {

            downLatch.countDown();

            return randomSleepTime;

        }

    }

}

