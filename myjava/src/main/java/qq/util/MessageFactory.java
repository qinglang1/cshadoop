package qq.util;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

import qq.common.*;
import qq.server.QQServer;

/**
 *
 */
public class MessageFactory {

	/**
	 * 服务端从SocketChannel中解析客户端消息，转换成服务器消息
	 */
	public static BaseMessage parseClientMessageFromChannel(SocketChannel sc) throws Exception {

		ByteBuffer buf1 = ByteBuffer.allocate(1) ;
		int len = sc.read(buf1);
		if(len != 1){
			return null ;
		}
		buf1.flip();

		//得到消息类型
		int msgType = buf1.get(0) ;

		switch (msgType){
			//群聊
			case BaseMessage.CLIENT_TO_SERVER_CHATS:
			{
				ServerChatsMessage msg = new ServerChatsMessage();
				//4字节缓冲区
				ByteBuffer buf4 = ByteBuffer.allocate(4);
				sc.read(buf4);
				//取出消息长度
				int msgLen = DataUtil.bytes2Int(buf4.array());

				//n个字节缓冲区
				ByteBuffer bufn = ByteBuffer.allocate(msgLen);
				sc.read(bufn);
				msg.setMessageBytes(bufn.array());
				msg.setSenderAddrBytes(QQUtil.getRemoteAddrBytes(sc.socket()));
				return msg;
			}

			//私聊
			case BaseMessage.CLIENT_TO_SERVER_CHAT:
			{
				ServerChatMessage msg = new ServerChatMessage();
				//接受者地址长度
				buf1.clear() ;
				sc.read(buf1) ;
				buf1.flip() ;
				int recvAddrLen = buf1.get(0) ;

				//接受者地址
				ByteBuffer bufn = ByteBuffer.allocate(recvAddrLen) ;
				sc.read(bufn) ;
				bufn.flip() ;

				//给msg中的private byte[] setRecvAddrBytes 赋值，以便后期组装报文
				msg.setRecvAddrBytes(bufn.array());   //未知  为何ServerChatMessage没有组装接收者地址

				//给msg中的private byte[] setSendAddrBytes 赋值，以便后期组装报文
				msg.setSenderAddrBytes(QQUtil.getRemoteAddrBytes(sc.socket()));

				//消息长度
				ByteBuffer buf4 = ByteBuffer.allocate(4) ;

				sc.read(buf4) ;
				buf4.flip();
				int msgLen = DataUtil.bytes2Int(buf4.array()) ;

				//消息内容
				bufn = ByteBuffer.allocate(msgLen) ;

				sc.read(bufn);
				bufn.flip();

				msg.setMessageBytes(bufn.array());
				return msg ;
			}
			//刷新好友
			case BaseMessage.CLIENT_TO_SERVER_REFRESH_FRIENDS:
			{
				ServerRefreshFriendsMessage msg = new ServerRefreshFriendsMessage();
				//通过QQServer的单例模式获取实时的好友列表，同时对好友列表实行串行化，由List<String>型转换为byte[]型
				msg.setFriendsBytes(QQServer.getInstance().getFriendsBytes());
				return msg ;
			}
		}

		//如果消息类型msgType不是以上类型的任何一种，则返回null
		return null ;
	}

	/**
	 *客户端从socket中解析服务器消息
	 */
	public static BaseMessage parseServerMesageFromSocket(Socket s) throws Exception {
		//
		InputStream in = s.getInputStream();
		//消息类型
		byte[] bytes1 = new byte[1] ;
		in.read(bytes1) ;
		int msgType = bytes1[0] ;

		//
		switch (msgType){
			//群聊
			case BaseMessage.SERVER_TO_CLIENT_CHATS:
			{   //读取发送者地址长度到bytes1中
				in.read(bytes1);     //此次读取的是发送者地址长度到bytes1中，发送者地址长度为一个字节，把bytes1中原有的一个字节的消息类型给覆盖了。并不是说把消息类型再读一遍
				int senderAddrLen = bytes1[0];

				byte[] bytesn = new byte[senderAddrLen];
				//读取发送地址到bytesn中
				in.read(bytesn);
				//发送地址
				String senderAddrStr = new String(bytesn);

				byte[] bytes4 = new byte[4];
				//读取消息长度到bytes4中
				in.read(bytes4);
				int msgLen = DataUtil.bytes2Int(bytes4);

				bytesn = new byte[msgLen];
				//读取消息内容到bytesn中
				in.read(bytesn);
				String msg = new String(bytesn);

				ClientChatsMessage msg0 = new ClientChatsMessage();   //未知  此处为何还要创建一个ClientChatsMessage对象
				msg0.setMessage(msg);
				msg0.setSenderAddr(senderAddrStr);  //未知
				return msg0;
			}

			//私聊
			case BaseMessage.SERVER_TO_CLIENT_CHAT:
			{
				//读取发送地址长度
				in.read(bytes1);
				int senderAddrLen = bytes1[0];

				byte[] bytesn = new byte[senderAddrLen];
				//读取发送地址内容
				in.read(bytesn);
				//发送地址
				String senderAddrStr = new String(bytesn);

				byte[] bytes4 = new byte[4];
				//读取消息长度
				in.read(bytes4);
				int msgLen = DataUtil.bytes2Int(bytes4);

				bytesn = new byte[msgLen];
				//读取消息内容
				in.read(bytesn);
				String msg = new String(bytesn);

				ClientChatMessage msg0 = new ClientChatMessage();   //未知
				msg0.setMessage(msg);
				msg0.setSenderAddr(senderAddrStr);    //未知
				return msg0;
			}
			//好友列表
			case BaseMessage.SERVER_TO_CLIENT_REFRESH_FRIENDS:
			{
				byte[] bytes4 = new byte[4];
				//读取好友列表长度
				in.read(bytes4);
				int friendsDataLen = DataUtil.bytes2Int(bytes4);

				byte[] bytesn = new byte[friendsDataLen];
				//读取好友列表内容
				in.read(bytesn);
				//对读取到的好友列表进行反串行化，由byte[]型转换为List<String>型
				List<String> friends = (List<String>) DataUtil.deserialData(bytesn);
				ClientRefreshFriendsMessage msg = new ClientRefreshFriendsMessage() ;  //未知
				msg.setFriendsList(friends);  //未知
				return msg ;
			}
		}
		return null ;
	}
}
