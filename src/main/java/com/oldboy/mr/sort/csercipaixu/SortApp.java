package com.oldboy.mr.sort.csercipaixu;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


public class SortApp{
    public static void main(String[] args) throws Exception {
        Configuration conf=new Configuration();
        conf.set("fs.defaultFS","file:///");
        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("ercipaixu");
        job.setJarByClass(SortApp.class);
        job.setMapperClass(SortMapper.class);
        job.setPartitionerClass(MyHashPartition.class);
        job.setGroupingComparatorClass(MyGroupComparator.class);
        job.setReducerClass(SortReducer.class);
        job.setMapOutputKeyClass(Compkey.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        Path pin = new Path("d:/csercipaixu/kv.txt");
        Path pout = new Path("d:/csercipaixu/out");
        FileInputFormat.setInputPaths(job,pin);

        if(fs.exists(pout)){
            fs.delete(pout,true);
        }
        FileOutputFormat.setOutputPath(job,pout);
        job.setNumReduceTasks(1);
        job.waitForCompletion(true);


    }


}
