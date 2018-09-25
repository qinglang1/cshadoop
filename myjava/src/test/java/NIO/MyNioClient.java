package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import static NIO.MyNioServer.getRemoteAddr;
import static NIO.MyNioServer.readStringFromChannel;

/**
 * 客户端
 */
public class MyNioClient {
	public static void main(String[] args) throws Exception {
		//开启套接字通道
		SocketChannel sc = SocketChannel.open();


		//服务端地址
		InetSocketAddress srvAddr = new InetSocketAddress("localhost" , 8888) ;
		//连接到服务端
		sc.connect(srvAddr) ;
		//配置非阻塞
		sc.configureBlocking(false);

		//开启挑选器
		Selector sel = Selector.open();

		//SocketChannel在客户端的挑选器中注册事件key
		SelectionKey key = sc.register(sel , SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT|SelectionKey.OP_READ) ;

		int index = 1 ;
		while(true){

			/**
			 * public abstract int select()
			 * select()方法为阻塞式方法，挑选出那些准备进行i/o操作的SocketChannel对应的key
			 * 挑选器至少挑选出一个key才会返回
			 * select()的返回值为挑选出的key的个数
			 *
			 */
			//因为客户端只有一个SocketChannel,所以客户端的挑选器中的keys集中只有一个key，所以只有一个key用来被挑选，
			//所以被挑选出来的key最多只有一个，所以客户端不用对selectedkeys进行迭代。
			int select = sel.select();

			/**
			 * public final boolean isConnectable()
			 * 判断此key对应的SocketChanne
			 *
			 */
			if(key.isConnectable()){
				System.out.println("可连接!");
				sc.finishConnect();
			}

			//是否可读
			if(key.isReadable()){
				/**
				 * public abstract SelectableChannel channel()
				 *Returns the channel for which this key was created.  This method will
				 */
				SocketChannel sc0 = (SocketChannel) key.channel();    //未知
				System.out.println(getRemoteAddr(sc0) + " 可读了");
				String msg = readStringFromChannel(sc0) ;
				System.out.println(getRemoteAddr(sc0) + " 发来消息: " + msg);
			}


			/**
			 *
			 * 客户端SocketChannel写操作不需要服务端先发过来信息，随时可写，所以key.isWritable()始终为true，则此if代码块中的内容肯定会被执行。
			 * 所以SocketChannel中可以不注册SelectionKey.OP_WRITE，如果一直想写，只需放在while(true)循环中即可
			 *
			 */

			if(key.isWritable()){
				System.out.println("可写");
				String msg = "tom" + index ;
				ByteBuffer buf = ByteBuffer.wrap(msg.getBytes()) ;
				//发送消息给服务器
				sc.write(buf) ;
				index ++ ;
			}
			//清空挑选集合
			sel.selectedKeys().clear();
			Thread.sleep(1000);
		}
	}
}
