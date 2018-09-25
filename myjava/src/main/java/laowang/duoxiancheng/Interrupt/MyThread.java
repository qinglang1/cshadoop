package laowang.duoxiancheng.Interrupt;

public class MyThread extends Thread {

    @Override
    public void run() {
        try {

            for (int i = 0; i <100 ; i++) {
            System.out.println(this.getName() + "--" + i);
                sleep(1000);

        }
        } catch (InterruptedException e) {
            System.out.println(this.getName()+"发生中断");
        }
    }
}
