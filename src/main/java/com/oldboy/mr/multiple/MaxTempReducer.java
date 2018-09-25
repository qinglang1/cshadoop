package com.oldboy.mr.multiple;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;

public class MaxTempReducer extends Reducer<Text,IntWritable,Text,DoubleWritable> {
    //定义变量
    MultipleOutputs mos ;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //初始化mos
        mos = new MultipleOutputs(context);
    }
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        Integer max = Integer.MIN_VALUE;

        for(IntWritable value : values){
            max = Math.max(max,value.get());
        }

        context.write(key, new DoubleWritable(max / 10.0));
       //括号中的指定输出的文件名前缀 1. "seq" 指定输出的文件名前缀 2.  key, new DoubleWritable(max / 10.0) 每行输出的内容  3. "D:/Temp/seq/"  SequenceFile文件存放的路径；
        mos.write("seq", key, new DoubleWritable(max / 10.0), "D:/Temp/seq/" );

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        //关流
        mos.close();
    }
}
