package com.oldboy.JavaHadoopAvroceshi;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;
import tutorialspoint.com.Emp;

import java.io.File;

public class TestAvro {
    public static void main(String[] args)  throws Exception{

        //
        DatumWriter<Emp> dw = new SpecificDatumWriter<Emp>(Emp.class);

        //入口点，实例化DataFileWriter
        DataFileWriter<Emp> dfw = new DataFileWriter<Emp>(dw);

        //开始序列化
        //参数2：输出文件路径
        dfw.create(Emp.SCHEMA$, new File("D:/avro/emp.avro"));

        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            Emp emp = new Emp();
            emp.setId(i);
            emp.setName("tom"+i);
            emp.setAge(i % 100);
            emp.setSalary(20000);
            emp.setAddress("昌平");
            dfw.append(emp);
        }
        System.out.println(System.currentTimeMillis() - start);
        //添加对象
        dfw.close();

    }


    @Test
    public  void testDese()  throws Exception{

        DatumReader<Emp> dr = new SpecificDatumReader<Emp>();
        DataFileReader<Emp> dfr = new DataFileReader<Emp>(new File("D:/avro/emp.avro"),dr);

        long start = System.currentTimeMillis();
        while(dfr.hasNext()){
            Emp emp = dfr.next();
            //System.out.println(emp);
        }
        System.out.println(System.currentTimeMillis() - start);
        dfr.close();
    }

    //不通过emp对象直接对schema操作
    @Test
    public void testSerial2() throws Exception{
        //通过schema文件生成schema对象
        Schema schema = new Schema.Parser().parse(new File("D:/avro/Emp.avsc"));
        //Instantiating the GenericRecord class.
        GenericRecord e1 = new GenericData.Record(schema);

        e1.put("name", "ramu");
        e1.put("id", 001);
        e1.put("salary",30000);
        e1.put("age", 25);
        e1.put("address", "chenni");

        DatumWriter dw = new SpecificDatumWriter();
        DataFileWriter<GenericRecord> dfw = new DataFileWriter<GenericRecord>(dw);

        dfw.create(schema,new File("D:/avro/emp.avro2"));

        dfw.append(e1);

        dfw.close();
    }

    //不通过emp对象直接对schema操作
    @Test
    public void testDeSerial2() throws Exception{
        //通过schema文件生成schema对象
        Schema schema = new Schema.Parser().parse(new File("D:/avro/Emp.avsc"));
        //Instantiating the GenericRecord class.
//        GenericRecord e1 = new GenericData.Record(schema);
//
//        e1.put("name", "ramu");
//        e1.put("id", 001);
//        e1.put("salary",30000);
//        e1.put("age", 25);
//        e1.put("address", "chenni");

        DatumReader dr = new SpecificDatumReader();
        DataFileReader<GenericRecord> dfr = new DataFileReader<GenericRecord>(new File("D:/avro/emp.avro2"),dr);

        while(dfr.hasNext()){
            GenericRecord record = dfr.next();
            System.out.println(record.get("name"));
        }
        dfr.close();

    }


}
