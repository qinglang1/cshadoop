package com.oldboy.mr.duowantop10;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.TreeSet;

public class TopReducer2 extends Reducer<Text,IntWritable,Text,IntWritable> {
    int i=0;
    TreeSet<CompKey> rc=new TreeSet<CompKey>();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        for (IntWritable value : values) {
         int   sum=value.get();
            rc.add(new CompKey(key.toString(),sum));
        }


    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (CompKey ck : rc){


            context.write(new Text(ck.getPass()),new IntWritable(ck.getSum()));
            i++;
            if (i==10){
                break;
            }
    }
}



}
