package com.oldboy.mr.tags;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.TreeSet;

public class TagReducer2 extends Reducer<Text,Text,Text,Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuffer sb = new StringBuffer();
        TreeSet<CompKey> treeSet = new TreeSet<CompKey>();
        for (Text value : values) {
            String[] arr = value.toString().split("_");
            String tag = arr[0];
            int num =Integer.parseInt(arr[1]) ;
            treeSet.add(new CompKey(tag,num));
        }

        for (CompKey compKey : treeSet) {
            String line = compKey.getTag() + "_" + compKey.getNum()+",";
            sb.append(line);
        }
        String s = sb.substring(0, sb.length() - 1);
        context.write(key,new Text(s));
    }
}
