package reflect.cat;

import org.junit.Test;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.HashMap;

import static com.sun.javafx.image.impl.ByteArgb.setter;

public class testintrospectorcopy {
    @Test
    public void testintrospector() throws Exception {
        jafeicat c1 = new jafeicat();
        c1.setColour("black");
        c1.setName("kkk");
        c1.setAge(8);
        //  jafeicat c2 = new jafeicat();
        person p1 = new person();
        //introspectorcopy(c1,c2);
       // introspectorcopy2(c1, p1);
        introspectorcopy3(c1, p1);
        System.out.println();

    }

    public void introspectorcopy(jafeicat c1, jafeicat c2) throws Exception {

        BeanInfo beanInfo = Introspector.getBeanInfo(c1.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            Method getter = pd.getReadMethod();
            Method setter = pd.getWriteMethod();

            if (getter != null) {
                Class<?> returnType = getter.getReturnType();
                System.out.println(returnType.getName());
                Object returnvalue = getter.invoke(c1);

                if (setter != null) {

                    setter.invoke(c2, returnvalue);

                    System.out.println();
                }


            }

        }
    }


    public void introspectorcopy2(jafeicat c1, person p1) throws Exception {
        HashMap<String, Method> map = new HashMap<>();
        BeanInfo beanInfoc1 = Introspector.getBeanInfo(c1.getClass());
        BeanInfo beanInfop1 = Introspector.getBeanInfo(p1.getClass());
        PropertyDescriptor[] pdsc1 = beanInfoc1.getPropertyDescriptors();
        PropertyDescriptor[] pdsp1 = beanInfop1.getPropertyDescriptors();
        for (PropertyDescriptor pdc1 : pdsc1) {
            String sourcename = pdc1.getName();
            System.out.println(sourcename);
            Method getter = pdc1.getReadMethod();
            System.out.println(getter.getName());
            if (getter != null && !sourcename.equals("class")) {
                map.put(sourcename, getter);
            }
        }

        for (PropertyDescriptor pdp1 : pdsp1) {

            //获取某字段的字段名
            String destname = pdp1.getName();
            System.out.println(destname);
            Method getter = null;

            //获取某字段在c1中的getter方法；
            //p1中的某字段在c1中可能不存在
            if (map.containsKey(destname)) {
                getter = map.get(destname);
            } else {
                continue;
            }

            //获取某字段在c1中的getter方法的返回值类型
            Class<?> sourcereturntype = getter.getReturnType();
            System.out.println(sourcereturntype.getName());

            //获取某字段在p1中的setter方法
            Method setter = pdp1.getWriteMethod();
            //获取某字段在p1中的setter方法的参数类型
            Class<?>[] destparametertypes = setter.getParameterTypes();

            //p1中某字段的setter方法可能不存在
            //c1中不一定有p1中的某个字段，也该就没有了该字段的getter方法
            if (getter != null && setter != null && destparametertypes.length == 1 && sourcereturntype.equals(destparametertypes[0])) {

                Object returnvalue = getter.invoke(c1);
                setter.invoke(p1, returnvalue);

                System.out.println();
            }
        }

        System.out.println(p1.getId() + "--" + p1.getAge());

    }

    public void introspectorcopy3(jafeicat c1, person p1) throws Exception {
        HashMap<String, Method> map = new HashMap<>();
        BeanInfo beanInfoc1 = Introspector.getBeanInfo(c1.getClass());
        BeanInfo beanInfop1 = Introspector.getBeanInfo(p1.getClass());
        PropertyDescriptor[] pdsc1 = beanInfoc1.getPropertyDescriptors();
        PropertyDescriptor[] pdsp1 = beanInfop1.getPropertyDescriptors();
        for (PropertyDescriptor pdc1 : pdsc1) {
            String sourcename = pdc1.getName();
            System.out.println(sourcename);
            Method getter = pdc1.getReadMethod();
            System.out.println(getter.getName());
            //保证某字段在c1中的getter方法不为空
            if (getter != null && !sourcename.equals("class")) {
                map.put(sourcename, getter);
            }
        }

        for (PropertyDescriptor pdp1 : pdsp1) {

            String destname = pdp1.getName();

            //保证map中含有p1中某字段
            if (!map.containsKey(destname)) {
                continue;
            }
            Method setter = pdp1.getWriteMethod();
            //保证某字段的setter方法不为空
            if (setter == null) {
                continue;
            }

            Method getter = map.get(destname);
            //保证某字段在c1中getter方法的返回值类型和某字段在p1中setter方法的参数类型相同
            if (setter.getParameterTypes().length == 1 && getter.getReturnType().equals(setter.getParameterTypes()[0])) {
                setter.invoke(p1, getter.invoke(c1));
            }
        }

        System.out.println(p1.getId() + "----" + p1.getAge());




    }
}