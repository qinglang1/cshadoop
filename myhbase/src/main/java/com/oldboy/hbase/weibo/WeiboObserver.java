package com.oldboy.hbase.weibo;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class WeiboObserver extends BaseRegionObserver {

    private static final String GUANZHU_TABLE_NAME = "weibo:guanzhu";
    private static final String FENSI_TABLE_NAME = "weibo:fensi";

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {

        //获取环境对象
        RegionCoprocessorEnvironment env = e.getEnvironment();
        //如果表名是关注表
        if(env.getRegionInfo().getTable().getNameAsString().equals(GUANZHU_TABLE_NAME)){

            //得到put的Cell
            //由于put时只有一个数据，即一个cell，所以获取map中第一个cell即可
            /*
            1. getFamilyCellMap()的返回值是NavigableMap<byte[], List<Cell>>类型,
             其中 byte[]类型的对象是 列族family, list<Cell>类型对应的对象是 该列族的所有cell组成的集合
            2. firstEntry()的返回值是 Map.Entry<K,V> 即NavigableMap<byte[], List<Cell>>的第一个键值对

             */

            List<Cell> cells = put.getFamilyCellMap().firstEntry().getValue();
            Cell cell = cells.get(0);

            //从cell中获取rowKey，即关注者
            String a = Bytes.toString(CellUtil.cloneRow(cell));
            String[] arr = a.split(",");

            //从cell中获取value，即被关注者
            String b = Bytes.toString(CellUtil.cloneValue(cell));

            String newa = arr[0];
            String newb = b+","+arr[1];


            //通过当前环境获取粉丝表
            Table table = (Table)env.getTable(TableName.valueOf(FENSI_TABLE_NAME));

            //构造新的put对象，将值对调，写入到粉丝表中
            Put newPut = new Put(Bytes.toBytes(newb));
            newPut.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("name"),Bytes.toBytes(newa));

            table.put(newPut);

            table.close();
        }
    }

    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        //获取环境对象
        RegionCoprocessorEnvironment env = e.getEnvironment();
        //如果表名是关注表
        if(env.getRegionInfo().getTable().getNameAsString().equals(GUANZHU_TABLE_NAME)){

            //得到delete的Cell
            //由于delete时只有一个数据，即一个cell，所以获取map中第一个cell即可
            List<Cell> cells = delete.getFamilyCellMap().firstEntry().getValue();
            Cell cell = cells.get(0);

            //从cell中获取rowKey，即关注者a,1534236083614
            String a = Bytes.toString(CellUtil.cloneRow(cell));
            String[] arr = a.split(",");

            //从cell中获取value，即被关注者c
            String b = Bytes.toString(CellUtil.cloneValue(cell));

            //c,1534236083614
            String newb = b+","+arr[1];


            //通过当前环境获取粉丝表
            Table table = (Table)env.getTable(TableName.valueOf(FENSI_TABLE_NAME));

            //构造新的delete对象，将值对调，将该数据从粉丝表中删除；
            Delete newDel = new Delete(Bytes.toBytes(newb));
            newDel.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("name"));

            table.delete(newDel);

            table.close();
        }
    }
}
