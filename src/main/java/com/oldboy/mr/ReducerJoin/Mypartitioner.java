package com.oldboy.mr.ReducerJoin;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Mypartitioner extends Partitioner<CompKey,Text> {

    public int getPartition(CompKey compKey, Text text, int i) {
           return  1;
    }
}
