package liduineicunhuishou;

import org.junit.Test;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class testCollectOffHeap {
    @Test
    public  void cleanOffHeap() throws Exception {
        /**
         * ByteBuffer是一个抽象类，DirectByteBuffer是其是其实现子类
         * DirectByteBuffer中有一个cleaner()方法，返回一个Cleaner类型的对象，
         * Cleaner类中有一个clean()方法
         *  public static ByteBuffer allocateDirect(int capacity) {
         *         return new DirectByteBuffer(capacity);
         *     }
         *
         */
        //buf1实际上是DirectByteBuffer类型的对象；
        ByteBuffer buf1 = ByteBuffer.allocateDirect(1024 * 1024 * 400);

        Class<?> clazz = Class.forName("java.nio.DirectByteBuffer");

        Method cleaner = clazz.getMethod("cleaner");
        cleaner.setAccessible(true);

        //
        Object cleanerduixiang = cleaner.invoke(buf1);
        Cleaner c1 = (Cleaner) cleanerduixiang;
        c1.clean();

        System.out.println();
    }


}
