package com.oldboy.mr.suijifenqu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.Random;

public class RandomPartition extends Partitioner<Text,IntWritable> {
    Random r =new Random();

    //getPartition中的形参i是第一次作业时reduce的个数
    public int getPartition(Text text, IntWritable intWritable, int i) {
        return r.nextInt(i);
    }
}
