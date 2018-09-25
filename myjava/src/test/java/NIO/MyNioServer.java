package NIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import java.util.Set;

/**
 * NIO服务器
 */
public class MyNioServer {
	public static void main(String[] args) throws Exception {
		//开启服务器套接字通道
		ServerSocketChannel ssc = ServerSocketChannel.open();

		InetSocketAddress addr = new InetSocketAddress("0.0.0.0" , 8888) ;
		//绑定地址
		ssc.bind(addr) ;

		//配置非阻塞
		ssc.configureBlocking(false) ;

		//开启挑选器
		Selector sel = Selector.open();

		//服务器套接字通道在挑选器中注册事件key
		ssc.register(sel , SelectionKey.OP_ACCEPT) ;
		//挑选器不断从set<SelectionKey>中挑选感兴趣的key并执行
		for(;;){
			//System.out.println("开始挑选..");
			//挑选器开始挑选感兴趣的key
			sel.select() ;
			//得到挑选出来的key集合
			Set<SelectionKey> keys = sel.selectedKeys();
			Iterator<SelectionKey> it = keys.iterator();
			//对挑选出来的key进行迭代
			while(it.hasNext()){
				SelectionKey key = it.next();

				try {

				//是否是可接受的
				if(key.isAcceptable()){
					//接受连接
					SocketChannel sc = ssc.accept();
					System.out.println("接受了新连接!");
					sc.configureBlocking(false) ;
					//ServerSocketChannel生成的SocketChannel也需要在挑选器中注册事件key，在下一轮for(;;)循环中等待被挑选
					sc.register(sel , SelectionKey.OP_READ | SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE) ;
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

				if(key.isConnectable()){
					SocketChannel sc0 = (SocketChannel) key.channel();
					System.out.println(getRemoteAddr(sc0) + " 可连接");
					sc0.finishConnect();
				}


				//可写
				if (key.isWritable()) {
					SocketChannel sc0 = (SocketChannel) key.channel();
					Date now = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					String dateStr = sdf.format(now);
					ByteBuffer buf = ByteBuffer.wrap(dateStr.getBytes());
					sc0.write(buf);
				}

			} catch (Exception e) {
				//注销key所对应的通道
				key.cancel();
			} finally {
				//删除当前的key
				it.remove();
			}



			}
		}
	}

	/**
	 * 从channel中读取string
	 */
	public static String readStringFromChannel(SocketChannel sc) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
		ByteBuffer buf = ByteBuffer.allocate(1024) ;
		while(sc.read(buf) != 0){
			buf.flip() ;
			baos.write(buf.array() , 0 , buf.limit());   //为什么是write
			buf.clear();
		}
		return new String(baos.toByteArray()) ;
	}

	/**
	 * 得到socket远程地址
	 */
	public static String getRemoteAddr(SocketChannel sc) {
		try {
			InetSocketAddress addr = (InetSocketAddress) sc.getRemoteAddress();
			String ip = addr.getAddress().getHostAddress();
			int port = addr.getPort() ;
			return ip + ":" + port ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null ;
	}
}
