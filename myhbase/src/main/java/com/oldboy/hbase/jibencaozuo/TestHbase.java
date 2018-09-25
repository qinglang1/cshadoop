package com.oldboy.hbase.jibencaozuo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class TestHbase {


    /**
     * 创建名字空间
     */
    @Test
    public void createNS() throws Exception {

        //初始化hbase 的conf
        Configuration conf = HBaseConfiguration.create();

        //通过连接工厂创建连接
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取hbase管理员
        Admin admin = conn.getAdmin();

        //通过构建器模式，创建namespace描述符
        NamespaceDescriptor nsd = NamespaceDescriptor.create("test2").build();

        admin.createNamespace(nsd);

        admin.close();

        conn.close();
    }

    /**
     * 创建表
     */
    @Test
    public void createTable() throws Exception {

        //初始化hbase 的conf
        Configuration conf = HBaseConfiguration.create();

        //通过连接工厂创建连接
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取hbase管理员
        Admin admin = conn.getAdmin();

        TableName table = TableName.valueOf("test:t1");

        HTableDescriptor htd = new HTableDescriptor(table);

        htd.addFamily(new HColumnDescriptor("f1"));
        htd.addFamily(new HColumnDescriptor("f2"));

        admin.createTable(htd);

        admin.close();

        conn.close();
    }

    /**
     * 插入数据
     */
    @Test
    public void putData() throws Exception {

        //初始化hbase 的conf
        Configuration conf = HBaseConfiguration.create();

        //通过连接工厂创建连接
        Connection conn = ConnectionFactory.createConnection(conf);

        HTable table = (HTable) conn.getTable(TableName.valueOf("test:t1"));

        //设置自动刷写为false
        table.setAutoFlush(false,false);

        //put列表
        List<Put> list = new LinkedList<Put>();

        DecimalFormat df = new DecimalFormat("000");

        long start = System.currentTimeMillis();
        for (int i = 1; i < 100; i++) {
            String str = df.format(i);
            //Bytes.toBytes(）可以将任意类型转换成字节数组
            Put put = new Put(Bytes.toBytes("row" + str));
            put.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("name"), Bytes.toBytes("tomas"+str));
            put.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("id"), Bytes.toBytes(str));
            put.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("age"), Bytes.toBytes(i % 100 +""));
            list.add(put);
        }

        table.put(list);
        table.flushCommits();

        table.close();
        conn.close();

        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 删除数据
     */
    @Test
    public void delData() throws Exception {

        //初始化hbase 的conf
        Configuration conf = HBaseConfiguration.create();

        //通过连接工厂创建连接
        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("test:t1"));

        //Bytes.toBytes(）可以将任意类型转换成字节数组
        Delete delete = new Delete(Bytes.toBytes("row1"));
        delete.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));

        table.delete(delete);

        table.close();
        conn.close();
    }

}
