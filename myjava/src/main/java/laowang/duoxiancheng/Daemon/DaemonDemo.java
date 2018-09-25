package laowang.duoxiancheng.Daemon;

public class DaemonDemo {

    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();

        t1.setName("boss");
        t2.setName("xiaobin1");
        t3.setName("xiaobin2");

        t2.setDaemon(true);
        t3.setDaemon(true);

        t1.start();
        t2.start();
        t3.start();
    }
}
