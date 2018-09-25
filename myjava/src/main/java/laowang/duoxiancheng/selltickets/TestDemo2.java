package laowang.duoxiancheng.selltickets;

public class TestDemo2 {
    public static void main(String[] args) {
        sellTicketsimpl st = new sellTicketsimpl();
        //多个线程操作同一个对象
        Thread t1 = new Thread(st, "窗口1");
        Thread t2 = new Thread(st, "窗口2");
        Thread t3 = new Thread(st, "窗口3");
        t1.start();
        t2.start();
        t3.start();
    }

}
