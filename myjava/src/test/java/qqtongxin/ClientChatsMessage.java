package qqtongxin;

/**
 * 客户端群聊消息
 */
//public class ClientChatsMessage extends BaseMessage{
//	//消息内容
//	private String message ;
//
//	public int getMessageType() {
//		return CLIENT_TO_SERVER_CHATS;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//}

public class  ClientChatsMessage extends  BaseMessage{

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getMessageType() {
		return CLIENT_TO_SERVER_CHATS;
	}
}