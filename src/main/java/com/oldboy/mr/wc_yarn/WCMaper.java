package com.oldboy.mr.wc_yarn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WCMaper extends Mapper<LongWritable,Text,Text,IntWritable> {
    /**
     *
       map位于while循环中，每读取一行调用一次map
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将将value变成字符串
        String line = value.toString();
        //将每一行已空格为界进行截串
        String[] words = line.split(" ");

        //将每一行的每个单词写出
        for(String word:words){
            context.write(new Text(word),new IntWritable(1));
        }



    }
}

