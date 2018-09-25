package com.oldboy.JavaHadoopAvroceshi;

import org.junit.Test;
import tutorialspoint.com.Emp;
import tutorialspoint.com.EmpWritable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestHadoop {

    @Test
    public void testSerial() throws Exception {

        long start = System.currentTimeMillis();
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:/avro/emp.hadoop"));
        EmpWritable ew = new EmpWritable();
        for (int i = 1; i <= 1000000; i++) {
            Emp emp = new Emp();
            emp.setId(i);
            emp.setName("tom" + i);
            emp.setAge(i % 100);
            emp.setSalary(20000);
            emp.setAddress("昌平");
            ew.setEmp(emp);
            //将ew内的成员变量emp内的各个成员变量写入到输出流
            ew.write(dos);
        }
        System.out.println(System.currentTimeMillis() - start);
        dos.close();
    }

    @Test
    public void testDeSerial() throws Exception {

        DataInputStream dis = new DataInputStream(new FileInputStream("D:/avro/emp.hadoop"));

        EmpWritable ew = new EmpWritable();
        long start = System.currentTimeMillis();
        for (int i = 1; i < 1000000; i++) {
            //将输入流中emp的各个成员变量赋值给EmpWritable中的emp的各个成员变量
            ew.readFields(dis);
            Emp emp = ew.getEmp();
        }
        System.out.println(System.currentTimeMillis() - start);
        dis.close();


    }

}
