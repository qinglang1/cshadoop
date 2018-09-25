package com.oldboy.hdfs.codec;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class zonghecodec {
    public  static void Compress(Class clazz){
        try {
            long start = System.currentTimeMillis();
            Configuration conf = new Configuration();
            CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz, conf);
            String ext=codec.getDefaultExtension();
            File file = new File("d:/codec/1.xml" + ext);
            CompressionOutputStream cos = codec.createOutputStream(new FileOutputStream(file));
            FileInputStream fis = new FileInputStream("d:/codec/1.xml");
            IOUtils.copyBytes(fis,cos,1024);
            cos.close();
            fis.close();
            long zongshichang = System.currentTimeMillis() - start;
            System.out.println("压缩方式:" + ext + "," + "压缩时间：" + zongshichang + "," + "压缩后文件大小：" + file.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  void Decompress(Class clazz){
        try {
            long start = System.currentTimeMillis();
            Configuration conf = new Configuration();
            CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz, conf);
            String ext=codec.getDefaultExtension();
            File file = new File("d:/codec/1.xml" + ext);
            CompressionInputStream cis = codec.createInputStream(new FileInputStream(file));
            FileOutputStream fos = new FileOutputStream("d:/codec/1.xml" + ext + "解压后的文件");
            cis.close();
            fos.close();
            long zongshichang = System.currentTimeMillis() - start;
            System.out.println("解压方式"+ext+","+"解压时间"+zongshichang);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {

        Class[]  clazzs={
                DeflateCodec.class,
                GzipCodec.class,
                BZip2Codec.class,
                LzopCodec.class,
                Lz4Codec.class,
                SnappyCodec.class
        };

        for(Class clazz: clazzs){
            Compress(clazz);
            Decompress(clazz);
        }

    }

}
