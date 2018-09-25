package com.oldboy.mr.sort.quanpaixu;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PassApp {
    public static void main(String[] args) throws Exception {
        //初始化配置文件
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        //初始化作业
        Job job = Job.getInstance(conf);
        //设置作业名
        job.setJobName("pass count");
        //设置job函数入口函数类
        job.setJarByClass(PassApp.class);
        //设置map的类
        job.setMapperClass(PassMapper.class);

        //设置partitioner类
        job.setPartitionerClass(PassPartitioner.class);
        //设置combiner类
//        job.setCombinerClass(PassReducer.class);
        //设置reduce的类
    //    job.setReducerClass(PassReducer.class);
        //设置map的输出键值对类
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置reduce的输出键值对类
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置输入流路径
        Path pin = new Path("d:/quanpaixu/duowan_user.txt");
        FileInputFormat.addInputPath(job,pin);
        //设置输出流路径
        Path pout = new Path("d:/quanpaixu/out");
        FileOutputFormat.setOutputPath(job,pout);
        //设置reduce个数
        job.setNumReduceTasks(3);
        //执行job作业
        job.waitForCompletion(true);
    }
}
