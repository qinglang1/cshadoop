package com.oldboy.JavaHadoopAvroceshi;

import org.junit.Test;
import tutorialspoint.com.Emp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestJava {

    @Test
    public void testSerial() throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/avro/emp.java"));

        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            Emp emp = new Emp();
            emp.setId(i);
            emp.setName("tom"+i);
            emp.setAge(i % 100);
            emp.setSalary(20000);
            emp.setAddress("昌平");
            oos.writeObject(emp);
        }
        System.out.println(System.currentTimeMillis() - start);
        oos.close();
    }

    @Test
    public void testDeSerial() throws Exception {

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:/avro/emp.java"));

        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            Emp emp = (Emp) ois.readObject();
            //System.out.println(emp);
        }
        System.out.println(System.currentTimeMillis() - start);
        ois.close();

    }
}
