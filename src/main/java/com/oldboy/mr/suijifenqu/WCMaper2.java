package com.oldboy.mr.suijifenqu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WCMaper2 extends Mapper<LongWritable,Text,Text,IntWritable> {


    /**
     *
       map位于while循环中，每读取一行调用一次map
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将将value变成字符串
        String line = value.toString();
        //将每一行已空格为界进行截串
        String[] strings = line.split("\t");
        context.write(new Text(strings[0]),new IntWritable(Integer.parseInt(strings[1])));


    }
}
