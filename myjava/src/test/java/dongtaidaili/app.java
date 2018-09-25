package dongtaidaili;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class app {
    public static void main(String[] args) {
        //类加载器  两种方法得到的loder相同
       // ClassLoader loader = ClassLoader.getSystemClassLoader();
        ClassLoader loader = welcomserviceimpl.class.getClassLoader();

        //接口集合
       Class[] inerfaces= {welcomservice.class,welcomservice2.class};
        //目标对象
        final welcomserviceimpl target = new welcomserviceimpl();

        /**
         *  InvocationHandler 接口
         * 匿名内部类实际上指的是某接口或父类的匿名的实现子类的对象
         *
         */
        //处理器
        InvocationHandler h = new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("hello world");
                long start = System.nanoTime();
                Object ret = method.invoke(target, args);
                long during = System.nanoTime() - start;
                System.out.println(method.getName() + "耗时： " + during);
                return ret;    //待测试
            }
        };


        /**
         * public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces, InvocationHandler h)
         * 括号中的参数：
         * loader  为接口实现类的类加载器
         * interfaces 为实现类的所有接口的类文件数组
         * h 为接口InvocationHandler的实现子类的对象
         *
         */
        //proxy代理人
        //创建代理对象
        Object o = Proxy.newProxyInstance(loader, inerfaces, h);

        welcomservice proxy1 = (welcomservice) o;

        welcomservice2 proxy2 = (welcomservice2) o;



        /**
         * 代理类对象调用代理类方法，会转为调用处理器中的invoke()方法。invoke()方法通过传入的参数，转而让接口的实现子类对象调用该方法。
         * 可以在invoke()方法中加入其他的处理业务
         * 代理类对象proxy2，sayhello2()方法，参数"qinglang" ,分别作为invoke(Object proxy, Method method, Object[] args)的参数传入到invoke()方法中
         */
        //先调用哪个方法，就是哪个方法耗时比较长
        proxy2.sayhello2("qinglang");// 308685
        proxy1.sayhello("tomas"); //   84869


    }


}
