package com.oldboy.mr.sort.csercipaixu;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SortMapper extends Mapper<LongWritable,Text,Compkey,NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] line = value.toString().split("\t");
        String year = line[0];
        int temp = Integer.parseInt(line[1]);
        Compkey compkey = new Compkey(year, temp);
        context.write(compkey,NullWritable.get());
    }
}
