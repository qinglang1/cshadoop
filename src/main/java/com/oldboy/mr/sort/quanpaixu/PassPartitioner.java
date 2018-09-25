package com.oldboy.mr.sort.quanpaixu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
//partitioner的泛型为输出k-v的类型
public class PassPartitioner extends Partitioner<Text,IntWritable> {
    public int getPartition(Text text, IntWritable intWritable, int i) {
        String pass = text.toString();
       if(pass.compareTo("1")<0) {
           return 0;
       }else if(pass.compareTo("a")<0){
           return 1;
       }else {
           return 2;
       }

    }
}
