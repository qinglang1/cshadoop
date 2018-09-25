package com.oldboy.hdfs.serialize;

import org.apache.hadoop.io.ObjectWritable;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class deserial {
    public static void main(String[] args) throws Exception {
        DataInputStream dis = new DataInputStream(new FileInputStream("d:/serial/person.h"));
  //      ObjectWritable ow = new ObjectWritable();
   //     ow.readFields(dis);
        System.out.println(dis.readUTF());
        System.out.println(dis.readInt());

//        Object o = ow.get();
//
//        Person p = (Person)o;
//        System.out.println(p.getAge());
//        System.out.println(p.getName());
 //       System.out.println(ow.get());

    }
}
