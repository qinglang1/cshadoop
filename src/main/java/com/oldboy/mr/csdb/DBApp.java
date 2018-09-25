package com.oldboy.mr.csdb;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;


import java.io.IOException;

public class DBApp {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
      //  FileSystem fs = FileSystem.get(conf);

        //通过配置文件初始化job
        Job job = Job.getInstance(conf);

        //设置job名称
        job.setJobName("DB word count");

        //job入口函数类
        job.setJarByClass(DBApp.class);

        //设置mapper类
        job.setMapperClass(DBMapper.class);

        //设置reducer类
        job.setReducerClass(DBReducer.class);


        //设置map的输出k-v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //设置reduce的输出k-v类型
        job.setOutputKeyClass(MyWritable2.class);
        job.setOutputValueClass(NullWritable.class);


        //配置数据库
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/big12";
        String user="root";
        String pass="root";

        DBConfiguration.configureDB(job.getConfiguration(),driver,url,user,pass);

        job.setInputFormatClass(DBInputFormat.class);

        //设置输入路径
        DBInputFormat.setInput(job,MyWritable.class,"select * from wc","select count(*) from wc");

        //设置输出路径
        DBOutputFormat.setOutput(job,"wordcount",2);

        job.setNumReduceTasks(1);
        job.waitForCompletion(true);

    }



}
