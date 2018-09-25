package com.oldboy.mr.ReducerJoin;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class ReduceJoinApp{
    public static void main(String[] args) throws Exception {
        Configuration conf=new Configuration();
        conf.set("fs.defaultFS","file:///");
        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("ReducerJoin");
        job.setJarByClass(ReduceJoinApp.class);
        job.setMapperClass(ReduceJoinMapper.class);
      //  job.setPartitionerClass(MyHashPartition.class);
        job.setGroupingComparatorClass(GroupComparator.class);
        job.setReducerClass(ReduceJoinReducer.class);
        job.setMapOutputKeyClass(CompKey.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        Path pin = new Path("d:/reducerjoin");
        Path pout = new Path("d:/reducerjoin/out");
        FileInputFormat.setInputPaths(job,pin);

        if(fs.exists(pout)){
            fs.delete(pout,true);
        }
        FileOutputFormat.setOutputPath(job,pout);
        job.setNumReduceTasks(1);
        job.waitForCompletion(true);


    }


}