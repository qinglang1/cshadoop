package com.oldboy.mr.ReducerJoin;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class ReduceJoinReducer extends Reducer<CompKey, Text, Text, NullWritable> {

    @Override
    protected void reduce(CompKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        Iterator<Text> iter = values.iterator();

        //iter.hasNext();
        //用户行
       String cusLine = iter.next().toString();
        String[] cusarr = cusLine.split("\t");


        while (iter.hasNext()) {

            //order行
            String orderLine = iter.next().toString();
            String[] orderarr = orderLine.split("\t");

            //拼串操作，并将其输出
            String newline=cusarr[0]+"\t"+cusarr[1]+"\t"+orderarr[1]+"\t"+orderarr[2];
            context.write(new Text(newline),NullWritable.get());




        }


    }
}
