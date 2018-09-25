package com.oldboy.mr.MapJoin;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MapJoinMapper extends Mapper<LongWritable,Text,Text,NullWritable> {
    HashMap<String, String> map;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        String customerpath = context.getConfiguration().get("customers.path");
        BufferedReader br = new BufferedReader(new FileReader(customerpath));
         map = new HashMap<String, String>();
        String line=null;
        while ((line=br.readLine())!=null){
            String id = line.split("\t")[0];
            map.put(id,line);

        }
        br.close();

    }

    @Override
    //读取的order中的内容
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arrord = value.toString().split("\t");
        String id = arrord[3];
        String cusline = map.get(id);
        String[] arrcus = cusline.split("\t");
        String name = arrcus[1];
        String ordnum = arrord[1];
        String ordpri = arrord[2];
        String hecheng=id+"\t"+name+"\t"+ordnum+"\t"+ordpri ;
        context.write(new Text(hecheng),NullWritable.get());



    }
}

