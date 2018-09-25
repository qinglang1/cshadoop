package qqtongxin;

/**
 * 服务器私聊
 */
//public class ServerChatMessage extends BaseMessage {
//	private  byte[] senderAddrBytes ;
//	private byte[] messageBytes ;
//
//	public byte[] getSenderAddrBytes() {
//		return senderAddrBytes;
//	}
//
//	public void setSenderAddrBytes(byte[] senderAddrBytes) {
//		this.senderAddrBytes = senderAddrBytes;
//	}
//
//	public byte[] getMessageBytes() {
//		return messageBytes;
//	}
//
//	public void setMessageBytes(byte[] messageBytes) {
//		this.messageBytes = messageBytes;
//	}
//
//	public int getMessageType() {
//		return SERVER_TO_CLIENT_CHAT;
//	}
//}


public class  ServerChatMessage extends BaseMessage{

	private byte[] sendaddr;

	private byte[] sendmessage;

	public byte[] getSendaddr() {
		return sendaddr;
	}

	public void setSendaddr(byte[] sendaddr) {
		this.sendaddr = sendaddr;
	}

	public byte[] getSendmessage() {
		return sendmessage;
	}

	public void setSendmessage(byte[] sendmessage) {
		this.sendmessage = sendmessage;
	}

	@Override
	public int getMessageType() {
		return SERVER_TO_CLIENT_CHAT;
	}
}