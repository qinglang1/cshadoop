package classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyClassLoader extends ClassLoader {
    //寻找类
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {


            String classpath = "D:/ceshi/myclassloader/" + name + ".class";

            FileInputStream fis = new FileInputStream(classpath);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = fis.read(bytes)) != -1) {
                System.out.println(bytes.length);
                //此处len是指从输入流中读取到的字节数，而byte.length恒等于1024所以len不能用byte.length代替
                baos.write(bytes, 0, len);
            }
            baos.close();
            fis.close();

            byte[] myclassbytes = baos.toByteArray();
            //定义类
            Class<?> myclass = defineClass(myclassbytes, 0, myclassbytes.length);
            return  myclass;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;
    }
}
