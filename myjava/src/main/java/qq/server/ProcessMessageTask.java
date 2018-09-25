package qq.server;

import qq.common.BaseMessage;
import qq.common.ServerChatMessage;
import qq.util.MessageFactory;
import qq.util.QQUtil;

import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 处理消息
 */
public class ProcessMessageTask implements  Runnable{

	private SelectionKey key  ; ;

	public ProcessMessageTask(SelectionKey key){
		this.key = key;
	}

	/**
	 *当key.isReadable()时；执行run()方法中的线程体
	 *
	 */

	public void run() {
		//获取QQServer的单例对象
		QQServer server = QQServer.getInstance();
		//获取该key对应的SocketChannel，key不一样，对应的SocketChannel不一样
		SocketChannel sc = (SocketChannel) key.channel();
		//获取该key相关联的lock对象，key不一样，对应的lock不一样
		//lock和channel一一对应
		ReentrantLock lock = (ReentrantLock) key.attachment();

		//lock是一个对象，对lock本身上锁，某对象上没上锁，是某对象的一个状态
		boolean b = lock.tryLock() ;
		if(b){
			try {
				//从服务端的SocketChannel中解析从客户端发来的信息，生成服务端消息
				BaseMessage msg = MessageFactory.parseClientMessageFromChannel(sc) ;
				if(msg != null){
					//msg.getMessageType 获取消息类型
					switch (msg.getMessageType()){
						case BaseMessage.SERVER_TO_CLIENT_CHATS:
							//向所有在线终端广播某终端发来的消息
							server.broadcastMessage(msg) ;
							break ;
						case BaseMessage.SERVER_TO_CLIENT_CHAT:
							//
							server.forwardMessage(msg , new String(((ServerChatMessage)msg).getRecvAddrBytes()));
							break ;
						case BaseMessage.SERVER_TO_CLIENT_REFRESH_FRIENDS:
							server.forwardMessage(msg , QQUtil.getRemoteAddr(sc.socket()));
							break ;
					}
				}
			} catch (Exception e) {
				//为什么不直接用上面的sc 直接sc.socket();
				Socket s = ((SocketChannel) key.channel()).socket();
				System.out.println(QQUtil.getRemoteAddr(s) + "下线了!");
				//remoteClient(String addr) 从Map中删除指定终端地址对应Socketchannel
				server.remoteClient(QQUtil.getRemoteAddr(s));
				//向所有在线的终端地址广播所有连接到QQServer的终端地址
				server.broadcastFriendLists();
			}
			lock.unlock();
		}
	}
}
