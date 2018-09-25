package com.oldboy.mr.MaxTemp;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MaxTempReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
    /**
     * reduce位于while循环中，每遇到一个不同的key调用一次reduce

     */
    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        double max = Double.MIN_VALUE;
        for(DoubleWritable value:values){
           max = Math.max(max, value.get());

        }
        context.write(key,new DoubleWritable(max));


    }
}
