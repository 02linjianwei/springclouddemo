package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyCallable  implements Callable {
    private String name;

    public MyCallable(String name) {
        this.name = name;

    }
    @Override
    public Object call() throws Exception {
        Thread.sleep(4000);
        return name;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Future>  list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Callable c = new MyCallable(i+" ");
            Future future =pool.submit(c);
            System.out.println("=================");
            list.add(future);
        }
        pool.shutdown();
        for (Future future : list) {
            System.out.println(future.get().toString()+"==================");
        }
    }
}
