package com.oldboy.JavaHadoopAvroceshi;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;
import tutorialspoint.com.Emp;

import java.io.File;
import java.io.IOException;


public class ceTestAvro {

@Test
    public  void avroserial() throws Exception {

        //1  括号内为Emp.class
        SpecificDatumWriter<Emp> dw = new SpecificDatumWriter<Emp>(Emp.class);
        //2
        DataFileWriter<Emp> dfw = new DataFileWriter<Emp>(dw);
        //3.指定输出路径和格式
        dfw.create(Emp.SCHEMA$,new File("D:/ceshi/avro/emp.avro"));
        long start = System.currentTimeMillis();
        for (int i = 1; i <1000000 ; i++) {
            Emp emp = new Emp();
            emp.setId(i);
            emp.setName("tom"+i);
            emp.setSalary(20000);
            emp.setAge(i%100);
            emp.setAddress("昌平");
            dfw.append(emp);
            System.out.println(emp);
        }
        dfw.close();

        System.out.println(System.currentTimeMillis() - start);   //29613

    }

@Test
    public void avrodeserial() throws Exception {

        SpecificDatumReader<Emp> dr = new SpecificDatumReader<Emp>();
        DataFileReader<Emp> dfr = new DataFileReader<Emp>(new File("D:/ceshi/avro/emp.avro"), dr);
        long start = System.currentTimeMillis();
        while (dfr.hasNext()){

            Emp emp = dfr.next();
            System.out.println(emp);

        }

       dfr.close();
        System.out.println(System.currentTimeMillis() - start);   //24045
    }



    //不通过Emp对象直接对schema操作,实现串行化

    @Test
    public  void avroserial2() throws Exception {

        Schema schema = new Schema.Parser().parse(new File("D:/ceshi/avro/emp.avsc"));

        SpecificDatumWriter dw = new SpecificDatumWriter();

        DataFileWriter<GenericData.Record> dfw = new DataFileWriter<GenericData.Record>(dw);

        dfw.create(schema,new File("D:/ceshi/avro/emp.avro2"));

        long start = System.currentTimeMillis();

        for (int i = 0; i <1000000 ; i++) {


            GenericData.Record gdr = new GenericData.Record(schema);


            gdr.put("name","tom"+i);
            gdr.put("id",i);
            gdr.put("salary",20000);
            gdr.put("age",i%100);
            gdr.put("address","昌平");

            System.out.println(gdr);
            dfw.append(gdr);

        }

        dfw.close();

        System.out.println(System.currentTimeMillis() - start);   //19023
    }

    @Test

    //不通过Emp对象直接对schema操作,实现反序列化
    public void avrodeserial2() throws Exception {

        Schema schema = new Schema.Parser().parse(new File("D:/ceshi/avro/emp.avsc"));

        SpecificDatumReader dr = new SpecificDatumReader();

        //GenericRecord 为什么不能替换为 GenericData.Record
        DataFileReader<GenericRecord> dfr = new DataFileReader<GenericRecord>(new File("D:/ceshi/avro/emp.avro2"), dr);

        long start = System.currentTimeMillis();
        for (GenericRecord record : dfr) {

            Object name = record.get("name");
            Object id = record.get("id");
            Object salary = record.get("salary");
            Object age = record.get("age");
            Object address = record.get("address");

            System.out.println("name:"+name+"\t"+"id:"+id+"\t"+"salary:"+salary+"\t"+"age:"+age+"\t"+"address:"+address);  //15807
        }

        dfr.close();

        System.out.println(System.currentTimeMillis() - start);
    }




}
