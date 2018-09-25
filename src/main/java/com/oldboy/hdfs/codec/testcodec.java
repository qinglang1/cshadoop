package com.oldboy.hdfs.codec;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class testcodec {
    public static void codeccompress() throws Exception {
        //通过反射获取CompressionCodec对象对象
        Configuration conf= new Configuration();
        CompressionCodec codec = ReflectionUtils.newInstance(GzipCodec.class, conf);
        //通过codec创建输出流，对文件进行压缩
        CompressionOutputStream cos = codec.createOutputStream(new FileOutputStream("d:/codec/1.xml.gz"));
        FileInputStream fis = new FileInputStream("d:/codec/1.xml");
        IOUtils.copyBytes(fis,cos,1024);
        IOUtils.closeStream(fis);
        IOUtils.closeStream(cos);
    }

    public static  void codecdecompress() throws Exception {
        Configuration conf = new Configuration();
        //通过反射获取CompressionCodec 的对象
        CompressionCodec codec = ReflectionUtils.newInstance(GzipCodec.class, conf);
        //通过codec创建输入流，对文件进行解压
        CompressionInputStream cis = codec.createInputStream(new FileInputStream("d:/codec/1.xml.gz"));
        //创建输出流
        FileOutputStream fos = new FileOutputStream("d:/codec/1copy.xml");
        IOUtils.copyBytes(cis,fos,1024);
        IOUtils.closeStream(cis);
        IOUtils.closeStream(fos);

    }

    public static void main(String[] args) throws Exception {
        codeccompress();
        codecdecompress();
    }
}
