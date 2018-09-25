package qq.server;

import qq.common.BaseMessage;
import qq.common.ServerRefreshFriendsMessage;
import qq.util.DataUtil;
import qq.util.IConstants;
import qq.util.QQUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 服务器,单例模式，只对一个对象中的成员进行操作
 */
public class QQServer {

	private static QQServer instance ;

	private QQServer(){
	}

	public static QQServer getInstance(){
		if(instance != null){
			return instance ;
		}
		synchronized (QQServer.class){
			if(instance ==null){
				instance = new QQServer() ;
			}
		}
		return instance ;
	}

	//所有客户端集合
	private Map<String , SocketChannel> allClients = new HashMap<String, SocketChannel>() ;

	/**
	 * 启动服务器
	 */
	public void start(){
		try {
			//启动线程池
			ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(IConstants.QQ_SERVER_THREAD_POOL_CORES);

			//创建通道
			ServerSocketChannel ssc = ServerSocketChannel.open();
			//配置阻塞模式
			ssc.configureBlocking(IConstants.QQ_SERVER_CHANNEl_BLOCKING_MODE) ;
			//绑定
			InetSocketAddress addr = new InetSocketAddress(IConstants.QQ_SERVER_BIND_HOST, IConstants.QQ_SERVER_BIND_PORT) ;
			ssc.bind(addr) ;

			//开启挑选器
			Selector sel = Selector.open();
			//注册
			ssc.register(sel, SelectionKey.OP_ACCEPT) ;

			for(;;){
				//开始挑选
				sel.select() ;
				Iterator<SelectionKey> it = sel.selectedKeys().iterator();
				while(it.hasNext()){
					SelectionKey key = it.next() ;
					try {
						//接受新连接，注册通道
						if(key.isAcceptable()){
							//终端有连接进来，在服务端生成对应的SocketChannel
							SocketChannel sc0 = ssc.accept() ;
							System.out.println("有人上线了!");
							//SocketChannel sc0配置非阻塞
							sc0.configureBlocking(IConstants.QQ_SERVER_CHANNEl_BLOCKING_MODE) ;

							//SocketChannel在挑选器上注册某些功能，生成具有某些功能的SocketChannel对应的key
							//
							SelectionKey key0 = sc0.register(sel , SelectionKey.OP_READ) ;
							//创建独占锁
							ReentrantLock lock = new ReentrantLock();
							/**
							 * SocketChannel与key关联，lock和key关联，即lock与SocketChannel也是关联的
							 * 每有一个终端连接到QQServer，就生成一个SocketChannel，将每一个SocketChannel都关联到一个不同的锁
							 * 不同的通道对应不同的锁
							 */

							//
							//
							key0.attach(lock) ;

							//放置信息到allClients集合中
							String remoteAddr = QQUtil.getRemoteAddr(sc0.socket()) ;
							allClients.put(remoteAddr, sc0) ;

							//广播好友列表
							broadcastMessage(genFriendListMessage());
						}

						//常规通道 即SocketChannel
						if(key.isReadable()){
							/**
							 * new ProcessMessageTask(key)生成一个与某个key相关即与某个channel相关的任务
							 * pool.execute()线程池执行任务，即执行ProcessMessageTask对象中的run()方法，run()方法运行结束，则该线程运行结束
							 *
							 */

							pool.execute(new ProcessMessageTask(key));
						}
					} catch (Exception e) {
						//撤销通道
						key.cancel();
						allClients.remove(QQUtil.getRemoteAddr(((SocketChannel)key.channel()).socket())) ;
					}
					finally {
						//删除key
						it.remove();
					}
				}
				//休眠毫秒数
				Thread.sleep(IConstants.QQ_SERVER_ROUNDROBIN_INTERVAL_SECONDS * 1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到所有连接到QQServer的终端地址的List<String>形式
	 */
	public List<String> getFriendsList(){
		return new ArrayList<String>(allClients.keySet());
	}

	/**
	 * 得到所有连接到QQServer的终端地址的byte[]形式
	 */
	public byte[] getFriendsBytes() throws Exception {
		return DataUtil.serialData(getFriendsList()) ;
	}

	/**
	 * 向所有连接到服务器的终端广播消息
	 */
	public void broadcastMessage(BaseMessage msg){
		System.out.println("广播消息");
		for(SocketChannel sc : allClients.values()){
			try {
				//msg.popPack()，将msg对象组装成报文
				sc.write(ByteBuffer.wrap(msg.popPack())) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *  服务器向某一在线recever转发来自某一sender的信息
	 */
	public void forwardMessage(BaseMessage msg , String recvAddr) throws Exception {
		//获得某一终端对应的服务器端的SocketChannel
		SocketChannel sc = allClients.get(recvAddr) ;
		if(sc != null){
			//写入报文
			sc.write(ByteBuffer.wrap(msg.popPack())) ;
		}
	}

	/**
	 * 生ServerRefreshFriendsMessage类型的消息，包含所有所有连接到QQServer的终端地址的信息
	 */
	public ServerRefreshFriendsMessage genFriendListMessage() throws Exception {
		ServerRefreshFriendsMessage msg = new ServerRefreshFriendsMessage();
		msg.setFriendsBytes(getFriendsBytes());
		return msg ;
	}
   //向所有在线的终端地址广播所有连接到QQServer的终端地址
	public void broadcastFriendLists(){
		try {
			ServerRefreshFriendsMessage msg = genFriendListMessage();
			broadcastMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从Map中删除指定终端地址对应Socketchannel
	 */
	public void remoteClient(String addr){
		allClients.remove(addr) ;    //删除key对应的value 即SocketChannel
	}

}
