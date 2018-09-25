package com.oldboy.screw;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Random;


public class WCMaper extends Mapper<LongWritable,Text,Text,IntWritable> {
     Random r=new Random();
     int reducenum;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
         reducenum = context.getNumReduceTasks();
    }

    /**
     *
       map位于while循环中，每读取一行调用一次map
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      //  int reducenum = context.getNumReduceTasks();


        //将将value变成字符串
        String line = value.toString();
        //将每一行已空格为界进行截串
        String[] words = line.split(" ");


        //将每一行的每个单词写出
        for(String word:words){
            int random = r.nextInt(reducenum);
            String newword = word + "_"+random;

            context.write(new Text(newword),new IntWritable(1));
        }



    }
}
