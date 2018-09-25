package com.oldboy.mr.sort.quanpaixu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class PassReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

    //reduce位于while循环中，每遇到一个不同的Key调用一次reduce
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum=0;
       for(IntWritable value:values){
           sum+=value.get();
       }

       context.write(key,new IntWritable(sum));
    }
}
