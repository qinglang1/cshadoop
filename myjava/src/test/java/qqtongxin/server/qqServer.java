package qqtongxin.server;

import qqtongxin.util.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class qqServer {

public  void  start()  {
    try {

    ServerSocketChannel ssc = ServerSocketChannel.open();

    //配置阻塞模式
    ssc.configureBlocking(Constants.QQ_SERVER_CHANNEL_BLOCKING_MODE);

     //绑定端口
    InetSocketAddress addr = new InetSocketAddress(Constants.QQ_SERVER_BIND_HOST, Constants.QQ_SERVER_BIND_PORT);
    ssc.bind(addr);

    //开启挑选器
    Selector sel = Selector.open();
    //服务器套接字通道在挑选器上注册事件key
    ssc.register(sel,SelectionKey.OP_ACCEPT);

    //创建线程池
    ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.QQ_SERVER_THREAD_POOL_CORES);

    for (; ;) {
        //挑选器开始挑选
        sel.select();
        Set<SelectionKey> selectionKeys = sel.selectedKeys();
        Iterator<SelectionKey> it = selectionKeys.iterator();
        while (it.hasNext()){
            SelectionKey key = it.next();
            try {


            if (key.isAcceptable()){
                //生成套接字通道
                SocketChannel sc0 = ssc.accept();
                sc0.configureBlocking(Constants.QQ_SERVER_CHANNEL_BLOCKING_MODE);
                SelectionKey key0 = sc0.register(sel, SelectionKey.OP_READ);
                //创建独占锁
                ReentrantLock lock = new ReentrantLock();
                //将key与lock相关联
                key0.attach(lock);
                Socket socket = sc0.socket();
            }
            //常规通道
            if (key.isReadable()){
                //客户端发来的信息可能较大，将读取客户端信息的操作放在线程池中运行，减少主线程压力
                pool.execute(new ProcessMessageTask(key));

            }
        } catch (Exception e) {
                key.cancel();
                //缺一步
        }
        finally {
                //删除key
                it.remove();
            }

        }

    }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}
