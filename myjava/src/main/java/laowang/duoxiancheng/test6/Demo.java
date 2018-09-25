package laowang.duoxiancheng.test6;

public class Demo {

	public static void main(String[] args) {//main方法本身是一个单独的线程
		//创建自定义对象
		MyThread mt = new MyThread();

		//调用start方法,启动线程
		mt.start();//
		//设置线程名称

		for(int i = 0;i<50000;i++){
			System.out.print("");
		}

		mt.setName("子线程1");

		//创建第二个线程对象
		MyThread mt2 = new MyThread();
		mt2.start();
		mt2.setName("子线程2");

		//让main线程执行长一点时间
		for(int i = 0;i<100;i++){
			//System.out.println(Thread.currentThread().getName() + " -- "+i);
			System.out.println(Thread.currentThread().getName() + " -- "+i);
		}
	}

}

