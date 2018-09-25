package com.oldboy.csweibo;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class csPutData  {

    public static void putfi (String fans ,String idol) {

        try {


            HBaseConfiguration conf = new HBaseConfiguration();
            Connection conn = ConnectionFactory.createConnection(conf);
            Table table = conn.getTable(TableName.valueOf("weibo1:guanzhu"));
            String key=fans+","+System.currentTimeMillis();


            Put put = new Put(Bytes.toBytes(key));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("idol:"),Bytes.toBytes(idol));

            table.put(put);

            table.close();
            conn.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {


//        putfi("a1","b");
        putfi("a2","b");
        putfi("a3","b");
        putfi("a4","b");
        putfi("a5","b");
        putfi("a6","b");
    }

}
