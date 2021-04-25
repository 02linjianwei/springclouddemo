package cn.ccb.clpm.data.DesignPattern;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyCallable implements Callable {
    private String name;

    public MyCallable(String name) {
        this.name = name;
    }
    @Override
    public Object call() throws Exception {
        System.out.println("线程内 "+name);
        return name;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Callable c = new MyCallable(i+" ");
            Future future = pool.submit(c);
            System.out.println("submit a call "+i);
            list.add(future);
        }
        pool.shutdown();
        Thread.sleep(3000);
        for (Future future : list) {
            System.out.println("线程结束 "+ future.get().toString());
        }

    }
}
