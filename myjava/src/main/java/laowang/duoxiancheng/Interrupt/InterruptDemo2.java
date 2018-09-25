package laowang.duoxiancheng.Interrupt;

public class InterruptDemo2 {
    public static void main(String[] args) {
        MyThread2 t1 = new MyThread2();
        t1.start();

        for (int i = 0; i <10 ; i++) {
            System.out.println(Thread.currentThread().getName()+"--"+i);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i==3){
                t1.stopthread();   //自定义方法
            }
        }


    }
}
