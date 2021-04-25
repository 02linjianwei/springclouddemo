package cn.ccb.clpm.data.DesignPattern;

import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedDemo {
    String lockA = "lockA";
    String lockB = "lockB";
    public static void main(String[] args) {
        final SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        final SynchronizedDemo synchronizedDemo2 = new SynchronizedDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo.generalMethod1();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo.generalMethod2();
            }
        }).start();

    }

    public  synchronized void generalMethod1() {
        try {
            for (int i = 1; i < 3; i++) {
                System.out.println("generalMethod1 "+i);
                Thread.sleep(3000);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
    public   void generalMethod2() {
        try {
            for (int i = 1; i < 3; i++) {
                System.out.println("generalMethod2 "+i);
                //Thread.sleep(3000);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
    public  void blockMethod1() {
        try {
            synchronized (lockA) {

                for (int i = 1; i < 3; i++) {
                    System.out.println("blockMethod1 "+i);
                    Thread.sleep(3000);
                }
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
    public   void blockMethod2() {
        try {
            synchronized (lockB) {

                for (int i = 1; i < 3; i++) {
                    System.out.println("blockMethod2 "+i);
                    Thread.sleep(3000);
                }
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}
