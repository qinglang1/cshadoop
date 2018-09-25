package qqtongxin.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class gezhongtest {

    public static void main(String[] args) throws IOException {
        String addr = "192.168 .18 .25";
        byte[] addrBytes = addr.getBytes();
        System.out.println(addrBytes.length);//15
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //往baos中写入
        int length = addrBytes.length;
       baos.write(235353555);

        
      //  byte[] bytes = baos.toByteArray();

       // System.out.println(bytes.length);//1

//        baos.write(addrBytes);
//        byte[] bytes = baos.toByteArray();
//        System.out.println(bytes.length);
//        ByteBuffer buffer = ByteBuffer.wrap(bytes);
       // buffer.
        ByteBuffer buff = ByteBuffer.allocate(2);
        byte b = buff.get(0);
        System.out.println(b);

    }
}
