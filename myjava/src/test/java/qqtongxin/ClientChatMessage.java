package qqtongxin;

///**
// * 客户端私聊消息
// */
//public class ClientChatMessage extends BaseMessage{
//	//消息内容
//	private String message ;
//
//	//接受地址
//	private String recvAddr ;
//
//	public int getMessageType() {
//		return CLIENT_TO_SERVER_CHAT;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public String getRecvAddr() {
//		return recvAddr;
//	}
//
//	public void setRecvAddr(String recvAddr) {
//		this.recvAddr = recvAddr;
//	}
//}


public class  ClientChatMessage extends  BaseMessage{

	private String message;

	private  String receiveaddr;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReceiveaddr() {
		return receiveaddr;
	}

	public void setReceiveaddr(String receiveaddr) {
		this.receiveaddr = receiveaddr;
	}

	@Override
	public int getMessageType() {
		return CLIENT_TO_SERVER_CHAT;
	}
}