package com.oldboy.cshbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;




public class csTestHbase {

    /**
     * 创建空间名
     */
    @Test
    public void createNS() throws Exception {
        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Admin admin = conn.getAdmin();

        NamespaceDescriptor nsd = NamespaceDescriptor.create("test").build();

        admin.createNamespace(nsd);

        admin.close();

        conn.close();

    }


    /**
     * 创建表
     */
    @Test

    public void cteateTable() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

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
     * 插入数据未优化
     */

    @Test
    public void putData() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("test:t1"));


        long start = System.currentTimeMillis();

        for (int i = 0; i <10000 ; i++) {

            Put put = new Put(Bytes.toBytes("row"+i));

            //其中f1为列族

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("tom"+i));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("id"), Bytes.toBytes(i+""));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"), Bytes.toBytes(i%100+""));

            table.put(put);

        }

        System.out.println(System.currentTimeMillis() - start);


        table.close();

        conn.close();


    }


    /**
     * 插入数据优化1
     */

    @Test
    public void putDatayouhua1() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("test:t1"));

        LinkedList<Put> list = new LinkedList<Put>();


        long start = System.currentTimeMillis();

        for (int i = 0; i <10000 ; i++) {

            Put put = new Put(Bytes.toBytes("row"+i));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("tom"+i));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("id"), Bytes.toBytes(i+""));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"), Bytes.toBytes(i%100+""));

            list.add(put);

        }

        table.put(list);

        System.out.println(System.currentTimeMillis() - start);//6323


        table.close();

        conn.close();


    }





    /**
     * 插入数据优化2
     */

    @Test
    public void putDatayouhua2() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

       HTable table = (HTable) conn.getTable(TableName.valueOf("test:t1"));//Table 强转成HTable

         table.setAutoFlush(false,false);


        long start = System.currentTimeMillis();

        for (int i = 0; i <10000 ; i++) {

            Put put = new Put(Bytes.toBytes("row"+i));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("tom"+i));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("id"), Bytes.toBytes(i+""));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"), Bytes.toBytes(i%100+""));

          table.put(put);

        }

        table.flushCommits();

        System.out.println(System.currentTimeMillis() - start);//5652


        table.close();

        conn.close();


    }



    /**
     * 插入数据优化3
     */

    @Test
    public void putDatayouhua3() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        HTable table = (HTable) conn.getTable(TableName.valueOf("test:t1"));

        LinkedList<Put> list = new LinkedList<Put>();


        long start = System.currentTimeMillis();

        for (int i = 0; i <10000 ; i++) {

            Put put = new Put(Bytes.toBytes("row"+i));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("tom"+i));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("id"), Bytes.toBytes(i+""));

            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"), Bytes.toBytes(i%100+""));

            list.add(put);

        }


        table.put(list);

        table.flushCommits();

        System.out.println(System.currentTimeMillis() - start);//2590 不一定准，可多试几次


        table.close();

        conn.close();


    }






    // 删除数据：

    @Test
    public void deletedata() throws Exception {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("test:t1"));

        Delete delete = new Delete(Bytes.toBytes("row1"));
        delete.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));
        table.delete(delete);

        table.close();
        conn.close();


    }
}