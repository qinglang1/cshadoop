package laowang.duoxiancheng.selltickets;

public class TestDemo {
    public static void main(String[] args) {
        SellTickets s1 = new SellTickets();
        SellTickets s2 = new SellTickets();
        SellTickets s3 = new SellTickets();
        s1.setName("窗口1");
        s2.setName("窗口2");
        s3.setName("窗口3");
        s1.start();
        s2.start();
        s3.start();
    }
}
