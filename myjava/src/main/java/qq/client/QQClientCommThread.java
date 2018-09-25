package qq.client;

import qq.common.BaseMessage;
import qq.common.ClientChatMessage;
import qq.common.ClientChatsMessage;
import qq.common.ClientRefreshFriendsMessage;
import qq.util.IConstants;
import qq.util.MessageFactory;
import qq.util.QQUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * client通信线程
 */
public class QQClientCommThread extends Thread {
	//
	private QQClientChatsUI ui ;

	public Socket sock ;
	//构造方法，传入ui
	public QQClientCommThread(QQClientChatsUI ui){
		this.ui = ui ;
		try {
			sock = new Socket(IConstants.QQ_CLIENT_SERVER_IP, IConstants.QQ_CLIENT_SERVER_PORT);  //未知
			//通过传入socket获得本地地址
			String localAddr = QQUtil.getLocalAddr(sock) ;
			//设置聊天页面的标题为本地地址
			ui.setTitle(localAddr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			for(;;){
				BaseMessage msg = MessageFactory.parseServerMesageFromSocket(sock) ;
				if(msg != null){
					System.out.println("收到服务器消息");
					switch (msg.getMessageType()){
						//群聊
						case BaseMessage.CLIENT_TO_SERVER_CHATS:
						{
							ClientChatsMessage msg0 = (ClientChatsMessage) msg;
							//更新历史区
							ui.updateHistory(msg0.getSenderAddr(),msg0.getMessage());
							break ;
						}
						//私聊
						case BaseMessage.CLIENT_TO_SERVER_CHAT:
						{
							ClientChatMessage msg0 = (ClientChatMessage) msg;
							String sendrAddr = msg0.getSenderAddr();
							String msgTxt = msg0.getMessage() ;
							QQClientChatSingleUI singleUI = ui.allSingleChart.get(sendrAddr) ;
							if(singleUI == null){
								singleUI = new QQClientChatSingleUI(sendrAddr ,this) ;
								ui.allSingleChart.put(sendrAddr, singleUI) ;
							}
							singleUI.setVisible(true);
							singleUI.updateHistory(sendrAddr, msgTxt);
							break ;
						}
						case BaseMessage.CLIENT_TO_SERVER_REFRESH_FRIENDS:
						{
							ClientRefreshFriendsMessage msg0 = (ClientRefreshFriendsMessage) msg;
							ui.refreshFriendList(msg0.getFriendsList());
							break ;
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送消息
	 */
	public void sendMessage(BaseMessage msg) throws Exception {
		sock.getOutputStream().write(msg.popPack());  //此处为何不需要强转BaseMessage
		sock.getOutputStream().flush();
	}
}
