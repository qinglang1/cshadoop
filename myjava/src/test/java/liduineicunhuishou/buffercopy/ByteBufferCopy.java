package liduineicunhuishou.buffercopy;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ByteBufferCopy {

    @Test
    public  void bytebufferallocate()  {
        try {


        ByteBuffer buf1 = ByteBuffer.allocate(1024 * 1024 * 2);
        FileInputStream fis = new FileInputStream("d:/ceshi/logagg/2018-07-01.log");
            FileChannel fcin = fis.getChannel();
            FileOutputStream fos = new FileOutputStream("d:/ceshi/logagg/copy1.txt");
            FileChannel fcout = fos.getChannel();
            int len =-1;
            long start = System.currentTimeMillis();
            while ((len=fcin.read(buf1))!=-1){
                buf1.flip();
                fcout.write(buf1);
                buf1.clear();
            }
            System.out.println(System.currentTimeMillis() - start);//159

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public  void bytebufferallocatedirect(){


        try {

            ByteBuffer buf1 = ByteBuffer.allocateDirect(1024 * 1024 * 2);
            FileInputStream fis = new FileInputStream("d:/ceshi/logagg/2018-07-01.log");
            FileChannel fcin = fis.getChannel();
            FileOutputStream fos = new FileOutputStream("d:/ceshi/logagg/copy2.txt");
            FileChannel fcout = fos.getChannel();
            int len =-1;
            long start = System.currentTimeMillis();
            while ((len=fcin.read(buf1))!=-1){
                buf1.flip();
                fcout.write(buf1);
                buf1.clear();
            }
            System.out.println(System.currentTimeMillis() - start);//82

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
