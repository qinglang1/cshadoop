package com.oldboy.cshbase.TableFormat;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;


//ImmutableBytesWritable 偏移量，无实际意义， Result 相当于一个列族中，所有cell的集合
public class TableInputMapper extends Mapper<ImmutableBytesWritable,Result,Text,IntWritable> {

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        List<Cell> cells = value.listCells();

        for (Cell cell : cells) {
            String[] words =new String(CellUtil.cloneValue(cell)).split(" ");
            for (String word : words) {
                context.write(new Text(word),new IntWritable(1));
            }
            String s = new String(key.get());
            System.out.println(s);//row1 row2 row2

        }
    }
}
