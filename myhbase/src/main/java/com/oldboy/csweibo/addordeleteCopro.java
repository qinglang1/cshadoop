package com.oldboy.csweibo;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

public class addordeleteCopro {


    @Test
    //创建表时添加协处理器
    public  void addcopro1() throws Exception {
        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Admin admin = conn.getAdmin();
        TableName tableName = TableName.valueOf("weibo1:guanzhu");
        HTableDescriptor htd = new HTableDescriptor(tableName);
        htd.addFamily(new HColumnDescriptor("f1"));
        //建表前添加协处理器
        htd.addCoprocessor("com.oldboy.csweibo.csWeiboObserver",new Path("/myhbase.jar"),0,null);
        //建表
        admin.createTable(htd);
        admin.close();
        conn.close();

    }
    @Test
    //表已存在添加协处理器
    public  void addcopro2() throws Exception {
        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Admin admin = conn.getAdmin();
        TableName tableName = TableName.valueOf("weibo1:guanzhu");
        admin.disableTable(tableName);

        HTableDescriptor htd = new HTableDescriptor(tableName);
        htd.addFamily(new HColumnDescriptor("f1"));
        htd.addCoprocessor("com.oldboy.csweibo.csWeiboObserver",new Path("/myhbase.jar"),0,null);

        //添加协处理器
        admin.modifyTable(tableName,htd);

        admin.enableTable(tableName);
        admin.close();
        conn.close();

    }




    @Test
   //删除某表的协处理器
    public  void deletecopro() throws Exception {
        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Admin admin = conn.getAdmin();
        TableName tableName = TableName.valueOf("weibo1:guanzhu");
        admin.disableTable(tableName);

        HTableDescriptor htd = new HTableDescriptor(tableName);
        htd.addFamily(new HColumnDescriptor("f1"));

        //删除协处理器
        admin.modifyTable(tableName,htd);

        admin.enableTable(tableName);
        admin.close();
        conn.close();

    }





}
