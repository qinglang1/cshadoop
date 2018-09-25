package lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018/9/9.
 */
public class SaleDemo {

	public static void main(String[] args) {
		new Sales("s1").start();
		new Sales("s2").start();
	}


	//票池
	static class TicketPool{
		//票数
		private int tickets = 100 ;

		//重入锁
		private static ReentrantLock lock = new ReentrantLock() ;

		//
		private static TicketPool instance ;

		//私有化构造方法
		private TicketPool(){
		}
        //TicketPool的成员方法，通过调用私有化的构造方法创建一个TicketPool对象；
		public static TicketPool getInstance(){
			if(instance != null){     //未知
				return instance ;
			}
			lock.lock();
			if(instance == null){
				//使用构造方法构造对象
				instance = new TicketPool() ;
			}
			lock.unlock();
			return instance ;
		}

		/**
		 * 取票
		 */
		public int getTicket(){
			boolean b = lock.tryLock();
			if(!b)
				return 0 ;
			int tmp = tickets ;
			if(tmp == 0){
				lock.unlock();
				return -1 ;
			}
			else{
				tickets -- ;
				lock.unlock();
				return tmp ;
			}
		}
	}

	/**
	 * 售票员
	 */
	static class Sales extends Thread{
		private String ssname ;
		private Sales(String ssname){
			this.ssname = ssname ;
		}

		public void run() {
			TicketPool pool = TicketPool.getInstance();
			for(;;){
				int n = pool.getTicket();
				if(n == 0){
					continue;
				}
				else if(n == -1){
					//结束线程
					break ;
				}
				else{
					System.out.printf("%s : %d\r\n" , ssname , n);
				}
			}
		}
	}
}
