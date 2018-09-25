package laowang.duoxiancheng.yield;

public class YieldDemo {

    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        //t1线程执行完之后，主线程才能执行
        t2.start();

        for (int i = 0; i <100 ; i++) {
            System.out.println(Thread.currentThread().getName() + "--" + i);
            Thread.currentThread().yield();
        }
    }
    }
