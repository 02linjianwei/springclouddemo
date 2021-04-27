package cn.myprojectdemo.data.DesignPattern;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author Worm
 * @Date 2020/10/6 10:38
 * @Version 1.0
 **/
public class BossWithResult implements Runnable {

    private CountDownLatch downLatch;

    //保存每个工人的工作时间

    private List<Future> workTimeUseList;


    public BossWithResult(CountDownLatch downLatch, List<Future> workTimeUseList) {

        this.downLatch = downLatch;

        this.workTimeUseList = workTimeUseList;

    }


    @Override

    public void run() {

        try {

            System.out.println("i am boss,i wait the job ok");

//如果不用CountDownLatch,worker，boss线程并发执行。
// 那么在这里如果先执行到workTimeFuture.get()时，就是自动挂起等着，哪个完成了输出哪个。
// 如果用了CountDownLatch，worker，boss线程并发执行。
// 但是boss会在这里等着所有工人完工，workTimeFuture.get()输出就会很整齐

//            downLatch.await();

            System.out.println("job ok haha.....");

            for (Future<Integer> workTimeFuture : workTimeUseList) {

                System.out.println("work time is :" + workTimeFuture.get());

            }

        } catch (InterruptedException | ExecutionException e) {

            e.printStackTrace();

        }

    }

}

