package com.oldboy.mr.sort.quanpaixu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class PassMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
    //map位于while循环中，每一行调用一次map
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] arr = line.split("\t");
        if (arr.length>=3&&arr[2]!=null){
            context.write(new Text(arr[2]),new IntWritable(1));
        }
    }
}
