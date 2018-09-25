package com.oldboy.mr.multiple;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;

public class MaxTempMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    //定义变量
    MultipleOutputs mos ;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        //初始化mos
        mos = new MultipleOutputs(context);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String year = line.substring(15, 19);
        int temp = Integer.parseInt(line.substring(87, 92));

        if (temp != 9999) {
            context.write(new Text(year), new IntWritable(temp));
        }

        //括号中的指定输出的文件名前缀 1. "text" 指定输出的文件名前缀 2. new Text(year), new IntWritable(temp) 每行输出的内容  3. "D:/Temp/text"  text文件存放的路径；
        mos.write("text", new Text(year), new IntWritable(temp), "D:/temp/text");

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        mos.close();
    }
}
