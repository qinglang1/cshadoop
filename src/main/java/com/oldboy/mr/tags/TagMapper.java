package com.oldboy.mr.tags;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class TagMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split("\t");
        String id = arr[0];
        List<String> list = Utils.parsejson(arr[1]);
        for (String s : list) {
            String newline = id + "_" + s;
            context.write(new Text(newline),new IntWritable(1));

        }
    }
}
