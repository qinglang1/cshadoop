package com.oldboy.mr.sort.csercipaixu;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyHashPartition extends Partitioner<Compkey,NullWritable>  {
    public int getPartition(Compkey compkey, NullWritable nullWritable, int i) {
     return    compkey.getYear().hashCode()&Integer.MAX_VALUE%i;
    }
}
