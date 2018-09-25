package com.oldboy.avro;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import tutorialspoint.com.Emp;

import java.io.File;
import java.io.IOException;

public class TestAvro {
    public static void main(String[] args) throws Exception {
      Emp emp=  new Emp();
      emp.setId(1);
      emp.setName("tom");
      emp.setAge(20);
      emp.setSalary(20000);
      emp.setAddress("昌平");

        SpecificDatumWriter<Emp> dw = new SpecificDatumWriter<Emp>(Emp.class);

        DataFileWriter<Emp> dfw = new DataFileWriter<Emp>(dw);

        dfw.create(emp.getSchema(),new File("D:/老男孩应用软件/avro/emp.avro"));
        
        dfw.append(emp);
        
        dfw.close();

        System.out.println("data sucessfully serialized");
    }
}
