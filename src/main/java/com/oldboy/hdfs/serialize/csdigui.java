package com.oldboy.hdfs.serialize;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class csdigui {


    public static void listfiles(String path) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        FileStatus[] statuses= fs.listStatus(new Path(path));
        for(FileStatus status:statuses){
            if(status.isDir()==true){
                System.out.println(status.getPath());
                listfiles(status.getPath().toString());
            }else{
                System.out.println(status.getPath().toString());
            }
        }



    }
    public static void main(String[] args) throws Exception {
        listfiles("/");

    }
}
