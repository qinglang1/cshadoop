package com.oldboy.mr.tags;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;

public class TagApp {
    public static void main(String[] args) throws Exception {
        Configuration conf =new Configuration();
        conf.set("fs.defaultFS","file:///");
        Job job = Job.getInstance(conf);
        FileSystem fs = FileSystem.newInstance(conf);

        job.setJobName("shangjia tag count");
        job.setJarByClass(TagApp.class);
        job.setMapperClass(TagMapper.class);
        job.setReducerClass(TagReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        Path pin = new Path("d:/tags/temptags.txt");
        Path pout = new Path("d:/tags/out");
        FileInputFormat.setInputPaths(job,pin);
        if (fs.exists(pout)){
            fs.delete(pout);
        }
        FileOutputFormat.setOutputPath(job,pout);
        job.setNumReduceTasks(1);

        boolean b = job.waitForCompletion(true);

        if (b){

            Job job2 = Job.getInstance(conf);
            job2.setJobName("shangjia tag count2");
            job2.setJarByClass(TagApp.class);
            job2.setMapperClass(TagMapper2.class);
            job2.setReducerClass(TagReducer2.class);
            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(Text.class);
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(Text.class);
            Path pin2 = new Path("d:/tags/out");
            Path pout2 = new Path("d:/tags/out2");
            FileInputFormat.setInputPaths(job2,pin2);
            if (fs.exists(pout2)){
                fs.delete(pout2,true);
            }
            FileOutputFormat.setOutputPath(job2,pout2);
            job2.setNumReduceTasks(1);

            b = job2.waitForCompletion(true);




        }

    }





}
