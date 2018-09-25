package laowang.duoxiancheng.Interrupt;

public class MyThread2 extends  Thread{
   private boolean flag=true;
    public void  stopthread(){
        flag=false;
    }
    @Override
    public void run() {
        int i=0;
        while (flag){

            System.out.println(this.getName() + "--" + i);
            i++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
