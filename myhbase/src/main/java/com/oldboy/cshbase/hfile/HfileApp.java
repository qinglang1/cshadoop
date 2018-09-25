package com.oldboy.cshbase.hfile;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class HfileApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        conf.set("fs.defaultFS","file:///");


        Job job = Job.getInstance(conf);

        job.setJobName("HFile");
        job.setJarByClass(HfileApp.class);

        job.setMapperClass(HfileMapper.class);
        job.setReducerClass(HfileReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Cell.class);

        FileInputFormat.addInputPath(job,new Path("D:/ceshi/duowantop10/duowan_user.txt"));
        job.setOutputFormatClass(HFileOutputFormat2.class);
        HFileOutputFormat2.setOutputPath(job,new Path("e:/hfile"));

        HFileOutputFormat2.configureIncrementalLoad(job,conn.getTable(TableName.valueOf("ns1:wc")),
                conn.getRegionLocator(TableName.valueOf("ns1:wc")));

        job.waitForCompletion(true);
    }


}