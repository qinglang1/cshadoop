package com.oldboy.hadoop.mr.maxtemp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/9/20.
 */
public class App {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf) ;
		job.setJobName("maxTemp");
		job.setJarByClass(App.class);

		job.setMapperClass(MaxTempMapper.class);
		job.setReducerClass(MaxTempReducer.class);

		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);

		job.setOutputKeyClass(IntWritable.class);
		job.setNumReduceTasks(5);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job , new Path("file:///D:\\mr\\temp3.dat"));
		FileOutputFormat.setOutputPath(job , new Path("file:///D:\\mr\\out") );

		job.waitForCompletion(true) ;
	}
}
