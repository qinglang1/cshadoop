package com.oldboy.mr.sort.ercipaixu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SortReducer extends Reducer<CompKey, NullWritable, Text, IntWritable> {
    @Override
    protected void reduce(CompKey key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        for(NullWritable  value: values){
            String year = key.getYear();
            int temp = key.getTemp();

            context.write(new Text(year), new IntWritable(temp));

        }




    }
}
