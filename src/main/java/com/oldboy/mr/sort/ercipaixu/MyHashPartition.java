package com.oldboy.mr.sort.ercipaixu;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyHashPartition extends Partitioner<CompKey,NullWritable> {
    @Override
    public int getPartition(CompKey compKey, NullWritable nullWritable, int numPartitions) {
        String year = compKey.getYear();

        return (year.hashCode() & Integer.MAX_VALUE) % numPartitions;

    }
}
