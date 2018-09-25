package com.oldboy.mr.wc_combiner;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WCApp  {
    public static void main(String[] args) throws Exception {
        //初始化配置文件
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        //通过配置文件初始化作业job
        Job job = Job.getInstance(conf);
        //设置作业名称
        job.setJobName("word count");
        //设置job 函数入口
        job.setJarByClass(WCApp.class);
        //设map的类
        job.setMapperClass(WCMaper.class);
        //设置combiner类
        job.setCombinerClass(WCReducer.class);
        //设置reduce的类
     //   job.setReducerClass(WCReducer.class);
        //设置map的输出的键值对的类
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置reduce的输出的键值对的类
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        //设置输入流的路径
        FileInputFormat.addInputPath(job,new Path("d:/wc/1.txt"));
        //设置输出流路径
        FileOutputFormat.setOutputPath(job,new Path("d:/wc/out"));
        //设置reduce的个数
        job.setNumReduceTasks(3);
        //执行job作业
        job.waitForCompletion(true);




    }
}
