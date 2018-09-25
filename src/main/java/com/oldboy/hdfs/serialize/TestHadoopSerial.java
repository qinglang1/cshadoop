package com.oldboy.hdfs.serialize;

import org.apache.hadoop.io.IntWritable;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestHadoopSerial {

    /**
     * 测hadoop序列化
     * @throws Exception
     */
    @Test
    public void testSerial() throws Exception{

        IntWritable iw = new IntWritable(100);

        DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:/serial/serial.h"));

        iw.write(dos);

        dos.close();

    }

    @Test
    public void testDeserial() throws Exception{
        DataInputStream dis = new DataInputStream(new FileInputStream("D:/serial/serial.h"));

        IntWritable iw = new IntWritable();

        iw.readFields(dis);

        int i = iw.get();

        dis.close();

        System.out.println(i);
    }

    /**
     * person序列化
     * @throws Exception
     */
    @Test
    public void testPersonSerial() throws  Exception{

        Person p = new Person("tom",20);
        PersonWritable pw = new PersonWritable();

        //在pw中将person对象传入
        pw.setPerson(p);

        DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:/serial/person.h"));

        pw.write(dos);

        dos.close();



    }


}
