package com.oldboy.mr.MaxTemp_yarn;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MaxTempMaper extends Mapper<LongWritable,Text,Text,DoubleWritable> {
    /**
     *
       map位于while循环中，每读取一行调用一次map
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将将value变成字符串
        String line = value.toString();
        //对每一行截取年份和日期
        String year = line.substring(15, 19);
        String temps = line.substring(87, 92);
        double temp = Double.parseDouble(temps)/10;



        //将每一行的年份和温度写出
        if(temp!=999.9) {
            context.write(new Text(year), new DoubleWritable(temp));
        }



    }
}
