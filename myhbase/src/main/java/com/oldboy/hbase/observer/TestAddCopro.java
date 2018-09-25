package com.oldboy.hbase.observer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class TestAddCopro {

    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        TableName table = TableName.valueOf("test:t3");
        HTableDescriptor htd = new HTableDescriptor(table);
        htd.addCoprocessor("com.oldboy.hbase.observer.MyObserver", new Path("/myhbase.jar"), 0, null);

        htd.addFamily(new HColumnDescriptor("f1"));

        admin.createTable(htd);

        admin.close();
        conn.close();


    }
}
