package qqtongxin;

/**
 * 服务器好友列表
 */
//public class ServerRefreshFriendsMessage extends BaseMessage {
//	private  byte[] friendsBytes ;
//
//	public byte[] getFriendsBytes() {
//		return friendsBytes;
//	}
//
//	public void setFriendsBytes(byte[] friendsBytes) {
//		this.friendsBytes = friendsBytes;
//	}
//
//	public int getMessageType() {
//		return SERVER_TO_CLIENT_REFRESH_FRIENDS;
//	}
//}


public  class  ServerRefreshFriendsMessage extends BaseMessage{

private   byte[] friends;

	public byte[] getFriends() {
		return friends;
	}

	public void setFriends(byte[] friends) {
		this.friends = friends;
	}

	@Override
	public int getMessageType() {
		return SERVER_TO_CLIENT_REFRESH_FRIENDS;
	}
}