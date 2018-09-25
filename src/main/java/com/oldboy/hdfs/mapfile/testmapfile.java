package com.oldboy.hdfs.mapfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;


public class testmapfile {
    public static void mapwriter() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        String path = "d:/seq/1.map";
        MapFile.Writer writer = new MapFile.Writer(conf, fs, path, IntWritable.class, Text.class, SequenceFile.CompressionType.BLOCK);
        for (int i = 1; i < 1000; i++) {
            IntWritable key = new IntWritable(i);
            Text value = new Text("helloworld" + i);
            writer.append(key, value);

        }
        writer.close();
    }
    public static void mapreader() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        String path = "d:/seq/1.map";
        MapFile.Reader reader = new MapFile.Reader(fs, path, conf);
        IntWritable key = new IntWritable();
        Text value = new Text();
        while(reader.next(key,value)){
            System.out.println("key:" + key.get()+"," + "value:" + value.toString());
        }
     reader.close();
    }

    public static void main(String[] args) throws Exception {
        mapwriter();
        mapreader();

    }
}

