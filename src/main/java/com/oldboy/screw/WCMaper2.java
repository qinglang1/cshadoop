package com.oldboy.screw;

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
        //将每一行进行截串
        String[] strings = line.split("\t");
        String word = strings[0].split("_")[0];
        int i = Integer.parseInt(strings[1]);

        //将每一行写出
            context.write(new Text(word),new IntWritable(i)); }



    }

