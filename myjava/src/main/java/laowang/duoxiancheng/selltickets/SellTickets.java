package laowang.duoxiancheng.selltickets;

public class SellTickets extends  Thread{
    static int tickets= 50;
    static Object o=new Object();
    @Override
    public void run() {
        synchronized(o) {
        while (true){

                if (tickets > 0) {

                    try {
                        //某一线程睡眠了，则在该线程睡眠的这段期间内，其他线程抢到CPU执行权的机会就增加了
                        //加入延时会让卖重复票的问题突显出来
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /**
                     * tickets--先运行，再自减，tickets--并不是原子性语句，可能还没等到自减就被cpu收回了执行权
                     */
                    System.out.println(this.getName() + "卖出第： " + tickets-- + "张票");

                }

            }
        }
    }
}
