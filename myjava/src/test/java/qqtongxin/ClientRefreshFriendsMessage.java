package qqtongxin;

/**
 * 客户端刷新好友
 */
//public class ClientRefreshFriendsMessage extends BaseMessage{
//
//	public int getMessageType() {
//		return CLIENT_TO_SERVER_REFRESH_FRIENDS;
//	}
//}


public class ClientRefreshFriendsMessage extends BaseMessage{


	@Override
	public int getMessageType() {
		return CLIENT_TO_SERVER_REFRESH_FRIENDS;
	}
}