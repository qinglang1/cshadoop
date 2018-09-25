package laowang.duoxiancheng.test6;

/*
 * 创建线程的第一种方式:
 * 1.自定义类继承Thread类
 * 2.重写其中的run方法(线程体)
 * 3.创建自定义类的对象.调用start方法,启动线程
 */
public class MyThread extends Thread{
	//
	@Override
	public void run() {
		//线程体
//		System.out.println("hello world");

		//让子线程也执行长一点时间
		for(int i = 0;i<100;i++){
			//this代表正在调用该run()方法的线程
			System.out.println(this.getName() + " -- " +i);
		}
	}
}
