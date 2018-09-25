package reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class testreflect {
@Test
    public  void testCopy() throws Exception {
    //Class<?> clazz = Class.forName("reflect.person");
    Class<person> clazz = person.class;
    Field[] fields1= clazz.getFields();
    for (Field field : fields1) {
        field.setAccessible(true);
    }
    Constructor<?> cons = clazz.getDeclaredConstructor(double.class, double.class, int.class, String.class);
    cons.setAccessible(true);

    Object o1 = cons.newInstance(60.0, 60.0, 60, "oxing");
    System.out.println();
    Class<?> o1Class = o1.getClass();
    Field age = o1Class.getDeclaredField("age");



    System.out.println();
    

}


 public void testCopy2(){




 }

}
