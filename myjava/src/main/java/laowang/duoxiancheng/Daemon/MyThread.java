package laowang.duoxiancheng.Daemon;

public class MyThread extends Thread {

    @Override
    public void run() {

        if (this.getName().equals("boss")) {

            for (int i = 0; i < 10; i++) {
                System.out.println(this.getName() + "--" + i);
            }
        } else {
            for (int i = 0; i < 1000; i++) {
                System.out.println(this.getName() + "--" + i);

            }

        }
    }
}
