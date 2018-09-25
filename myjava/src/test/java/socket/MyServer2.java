package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */
public class MyServer2 {
	public static void main(String[] args) throws Exception {
		//服务器套接字
		ServerSocket ss = new ServerSocket(8888);
		//
		while(true){
			System.out.println("开始接受..");
			//客户端套接字,阻塞模式
			Socket sock = ss.accept();
			System.out.printf("%s连进来了!!!\r\n" ,getRemoteAddr(sock));
			InputStream in = null;
			try {
				in = sock.getInputStream();
				String addr = getRemoteAddr(sock) ;
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while ((line = br.readLine()) != null) {
					System.out.printf("%s发来消息 : %s\r\n", addr, line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getRemoteAddr(Socket sock){
		//得到远程客户端的地址和端口
		InetSocketAddress addr = (InetSocketAddress) sock.getRemoteSocketAddress();
		String ip = addr.getHostName();
		int port = addr.getPort();
		return ip + ":" + port ;
	}
}
