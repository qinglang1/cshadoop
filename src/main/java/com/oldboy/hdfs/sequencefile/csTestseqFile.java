package com.oldboy.hdfs.sequencefile;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;


import java.io.IOException;

public class csTestseqFile {

    public static void testWriteSeq() throws Exception {
        Configuration conf= new Configuration();
        conf.set("fs.defaultFS","file:///");
        Path p = new Path("D:/seq/block.seq");
        FileSystem fs = FileSystem.get(conf);
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, p, IntWritable.class, Text.class, SequenceFile.CompressionType.BLOCK);
        for(int i=0;i<1000;i++){
            IntWritable key = new IntWritable(i);
            Text value = new Text("hello" + i);
            writer.append(key,value);
        }
        writer.close();

    }

    public static void testReadSeq() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        Path p = new Path("D:/seq/block.seq");
        FileSystem fs = FileSystem.get(conf);
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, p, conf);
        IntWritable key = new IntWritable();
        Text value = new Text();
        while(reader.next(key,value)){
            System.out.println("key:"+key.get()+"value:"+value.toString());
        }
        reader.close();


    }



    public static void main(String[] args) throws Exception {
        testWriteSeq();
        testReadSeq();

    }

    

}
