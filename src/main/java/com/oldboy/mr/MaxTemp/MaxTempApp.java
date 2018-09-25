package com.oldboy.mr.MaxTemp;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTempApp {
    public static void main(String[] args) throws Exception {
        //初始化配置文件
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");

        FileSystem fs = FileSystem.get(conf);
        //通过配置文件初始化作业job
        Job job = Job.getInstance(conf);
        //设置作业名称
        job.setJobName("max temp");
        //设置job 函数入口
        job.setJarByClass(MaxTempApp.class);
        //设map的类
        job.setMapperClass(MaxTempMaper.class);
        //设置combiner类
        job.setCombinerClass(MaxTempReducer.class);
        //设置reduce的类
      //  job.setReducerClass(MaxTempReducer.class);
        //设置map的输出的键值对的类
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);
        //设置reduce的输出的键值对的类
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);


        //设置输入流的路径
        Path pathin = new Path("d:/temp/temp");
        FileInputFormat.addInputPath(job,pathin);

        //设置输出流路径
        Path pathout = new Path("d:/temp/maxout");
        if(fs.exists(pathout)){
            fs.delete(pathout,true);

        }

        FileOutputFormat.setOutputPath(job,pathout);
        //设置reduce的个数
       // job.setNumReduceTasks(4);
        //执行job作业
        job.waitForCompletion(true);




    }
}
