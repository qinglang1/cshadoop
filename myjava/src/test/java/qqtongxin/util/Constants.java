package qqtongxin.util;

public class Constants {

public  static  final String QQ_SERVER_BIND_HOST=PropertiesUtil.getStringValue("qq.server.bind.host");

public  static  final int QQ_SERVER_BIND_PORT=PropertiesUtil.getIntValue("qq.server.bind.port");

public  static  final boolean QQ_SERVER_CHANNEL_BLOCKING_MODE=PropertiesUtil.getBooleanValue("qq.server.channel.blocking.mode");

public static final int QQ_SERVER_THREAD_POOL_CORES=PropertiesUtil.getIntValue("qq.server.thread.pool.cores");

   //测试
    public static void main(String[] args) {
        System.out.println(QQ_SERVER_BIND_HOST);
    }
}
