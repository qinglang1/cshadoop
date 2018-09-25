package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class csmynioserver {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress("0.0.0.0", 8888);
        ssc.bind(addr);
        ssc.configureBlocking(false);
        Selector sel = Selector.open();
        ssc.register(sel,SelectionKey.OP_ACCEPT);



    }



}
