package qqtongxin.server;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessMessageTask implements  Runnable{

    private SelectionKey key;

    public ProcessMessageTask(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {
        SocketChannel sc = (SocketChannel) key.channel();
        ReentrantLock lock = (ReentrantLock) key.attachment();
        boolean b = lock.tryLock();

        if(b){


            lock.unlock();
        }
    }
}
