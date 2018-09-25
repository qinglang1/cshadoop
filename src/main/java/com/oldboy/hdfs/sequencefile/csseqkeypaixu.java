package com.oldboy.hdfs.sequencefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Sorter;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class csseqkeypaixu {
   @Test
    public static void testWrite() throws Exception {
        Configuration conf= new Configuration();
        conf.set("fs.defaultFS","file:///");
        Path p = new Path("D:/seq/mergepart2.seq");
        FileSystem fs = FileSystem.get(conf);
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, p, IntWritable.class, Text.class, SequenceFile.CompressionType.BLOCK);
        for(int i=1;i<1000;i++){
            Random r = new Random();
            int j = r.nextInt(2000);
            IntWritable key = new IntWritable(j);
            Text value = new Text("hello" + j);
            if(i%5==0){
                writer.sync();
            }
            writer.append(key,value);
        }
        writer.close();

    }

    @Test
    public static void testRead() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        Path p = new Path("D:/seq/block1000.seq");
        FileSystem fs = FileSystem.get(conf);
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, p, conf);



        IntWritable key = new IntWritable();
        Text value = new Text();
        while(reader.next(key,value)){
            long position = reader.getPosition();
            System.out.println("key:"+key.get()+","+"value:"+value.toString()+","+"position:"+position);
        }
        reader.close();


    }
    @Test
     public static void  testSort() throws Exception {
         Configuration conf = new Configuration();
         conf.set("fs.defaultFS","file:///");
         FileSystem fs = FileSystem.get(conf);
         Path pathrandom = new Path("D:/seq/random.seq");
         Path pathsort = new Path("D:/seq/sort.seq");
         Sorter sorter = new Sorter(fs, IntWritable.class, Text.class, conf);
         sorter.sort(pathrandom,pathsort);
     }

     public static void  seekRead() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        FileSystem fs = FileSystem.get(conf);
        Path p = new Path("d:/seq/block1000.seq");
         SequenceFile.Reader reader = new SequenceFile.Reader(fs, p, conf);
         IntWritable key = new IntWritable();
         Text value = new Text();
         long key1head = reader.getPosition();//第一对键值对的开头
         System.out.println("第一对键值对的开头"+key1head);
         reader.next(key,value);
         long entry1 = reader.getPosition();
         System.out.println("key1:"+key.get()+","+"value1:"+value.toString()+","+"position:"+entry1);
         reader.next(key,value);
         long entry2 = reader.getPosition();
         System.out.println("key2:"+key.get()+","+"value2:"+value.toString()+","+"position:"+entry2);
         reader.seek(128);
         reader.next(key ,value);
         long entryseek = reader.getPosition();
         System.out.println("keyseek:"+key.get()+","+"valueseek:"+value.toString()+","+"position:"+entryseek);
         reader.next(key ,value);
         entryseek = reader.getPosition();
         System.out.println("keyseek:"+key.get()+","+"valueseek:"+value.toString()+","+"position:"+entryseek);

         //System.out.println("valueseek:"+value.toString()+","+"position:"+entryseek);

     }


@Test
    public static void  syncRead() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        FileSystem fs = FileSystem.get(conf);
        Path p = new Path("d:/seq/block1000.seq");
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, p, conf);
        IntWritable key = new IntWritable();
        Text value = new Text();
        long key1head = reader.getPosition();//第一对键值对的开头
        System.out.println("第一对键值对的开头"+key1head);

        reader.seek(32971);
        reader.next(key,value);
        long entrysync = reader.getPosition();
        System.out.println("keysync:"+key.get()+","+"valuesync:"+value.toString()+","+"position:"+entrysync);
        reader.next(key,value);
        long entry2 = reader.getPosition();
        System.out.println("key2:"+key.get()+","+"value2:"+value.toString()+","+"position:"+entry2);
    long tail = reader.getPosition();
    reader.seek(tail-29);
    reader.next(key ,value);
    long entryseek = reader.getPosition();
    System.out.println("keyseek:"+key.get()+","+"valueseek:"+value.toString()+","+"position:"+entryseek);
    reader.next(key ,value);
    entryseek = reader.getPosition();
    System.out.println("keyseek:"+key.get()+","+"valueseek:"+value.toString()+","+"position:"+entryseek);

    }
     public static void textmerge() throws Exception {
         Configuration conf = new Configuration();
         conf.set("fs.defaultFS","file:///");
         FileSystem fs = FileSystem.get(conf);
         Sorter sorter = new Sorter(fs, IntWritable.class, Text.class, conf);
         Path p1 = new Path("D:/seq/mergepart1.seq");
         Path p2 = new Path("D:/seq/mergepart2.seq");
         Path p3 = new Path("D:/seq/merge.seq");

         Path[] paths={p1,p2};
         sorter.merge(paths,p3);
     }





    public static void main(String[] args) throws Exception {
//          testWrite();
//        testSort();
//        textmerge();
          testRead();
 //        seekRead();
       syncRead();



    }


}
