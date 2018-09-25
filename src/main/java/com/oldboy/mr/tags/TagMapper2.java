package com.oldboy.mr.tags;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TagMapper2 extends Mapper<LongWritable,Text,Text,Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split("\t");
        String[] arr2 = arr[0].split("_");
        String id = arr2[0];
        String tag = arr2[1];
        String num= arr[1];
        String newline = tag + "_" + num;

        context.write(new Text(id),new Text(newline));


    }
}
