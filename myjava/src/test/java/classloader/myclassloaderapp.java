package classloader;

public class myclassloaderapp {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader();
        //加载类
        Class<?> myclass = myClassLoader.loadClass("byebyeimpl");
        System.out.println(myclass);
        Object o = myclass.newInstance();
        IByeBye o1 = (IByeBye) o;
        o1.saybyebye("qinglang");
  }
}

