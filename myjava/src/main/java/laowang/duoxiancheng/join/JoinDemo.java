package laowang.duoxiancheng.join;

public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        //t1线程执行完之后，主线程才能执行
        t1.join();

        t2.start();
        t2.join();

        for (int i = 0; i <100 ; i++) {
            System.out.println(Thread.currentThread().getName() + "--" + i);

        }
    }

}
