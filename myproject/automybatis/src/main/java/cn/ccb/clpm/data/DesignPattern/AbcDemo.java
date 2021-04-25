package cn.ccb.clpm.data.DesignPattern;

public class AbcDemo implements Runnable{
    private String flag = "A";
    private String name;
    private Object object = new Object();

    public AbcDemo(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            synchronized (object) {
                if ("A".equals(flag)) {
                    flag = "B";
                    System.out.print(name);
                    object.notifyAll();
                } else if("B".equals(flag)) {
                    flag="C";
                    System.out.print("B");
                    object.notifyAll();
                } else if ("C".equals(flag)) {
                    flag = "A";
                    System.out.print("C");
                    object.notifyAll();
                } else {
                    object.wait();
                }
            }
        } catch (Exception e) {
            System.out.println("====================="+e.fillInStackTrace());
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new AbcDemo("A")).start();
            new Thread(new AbcDemo("B")).start();
            new Thread(new AbcDemo("C")).start();
        }
    }
}
