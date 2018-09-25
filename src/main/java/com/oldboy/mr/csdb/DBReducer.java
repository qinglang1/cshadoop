package com.oldboy.mr.csdb;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
//DBOutputFormat规定了reduce端的输出格式为<k extends DBWritable, v>
public class DBReducer extends Reducer<Text,IntWritable,MyWritable2,NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
      int sum=0;
        for (IntWritable value : values) {
            sum+=value.get();
        }
    //    MyWritable2 mw = new MyWritable2(key.toString(), sum);
        MyWritable2 mw = new MyWritable2(key.toString(),sum);

        context.write(mw,NullWritable.get());
    }
}
