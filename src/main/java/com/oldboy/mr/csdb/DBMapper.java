package com.oldboy.mr.csdb;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;
//DBInputFormat中规定了从数据库输入Map的格式为<LongWritable,T extends DBWritable>
public class DBMapper extends Mapper<LongWritable,MyWritable,Text,IntWritable> {

    @Override
    protected void map(LongWritable key, MyWritable value, Context context) throws IOException, InterruptedException {
        String[] words = value.getLine().split(" ");
        for (String word : words) {
            context.write(new Text(word),new IntWritable(1));
        }
    }
}
