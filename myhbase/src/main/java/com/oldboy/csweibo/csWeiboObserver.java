package com.oldboy.csweibo;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.Store;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class csWeiboObserver  extends BaseRegionObserver {

  private   static  final  String GUANZHU_TABLE_NAME="weibo1:guanzhu";

  private   static  final  String FANS_TABLE_NAME="weibo1:fans";



    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        RegionCoprocessorEnvironment env = e.getEnvironment();

        if(env.getRegionInfo().getTable().getNameAsString().equals(GUANZHU_TABLE_NAME)){
            List<Cell> cells = put.getFamilyCellMap().firstEntry().getValue();
            Cell cell = cells.get(0);
            String row =Bytes.toString( CellUtil.cloneRow(cell));
            String[] splits = row.split(",");
            String fans = splits[0];
            String timestamp = splits[1];
            String idol = Bytes.toString(CellUtil.cloneValue(cell));
            String newrow = idol +","+ timestamp;
            String newvalue = fans;

         Table table =( Table)env.getTable(TableName.valueOf(FANS_TABLE_NAME));

            Put newput = new Put(Bytes.toBytes(newrow));
            newput.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("fans"),Bytes.toBytes(newvalue));
            table.put(newput);

            table.close();


        }




    }

    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {

        RegionCoprocessorEnvironment env = e.getEnvironment();
        if(env.getRegionInfo().getTable().getNameAsString().equals(GUANZHU_TABLE_NAME)){
            List<Cell> cells = delete.getFamilyCellMap().firstEntry().getValue();
            Cell cell = cells.get(0);
            String row =Bytes.toString( CellUtil.cloneRow(cell));
            String[] splits = row.split(",");
            String fans = splits[0];
            String timestamp = splits[1];
            String idol = Bytes.toString(CellUtil.cloneValue(cell));
            String newrow = idol +','+ timestamp;
            String newvalue = fans;

            Table table =( Table)env.getTable(TableName.valueOf(FANS_TABLE_NAME));

            Delete newdelete = new Delete(Bytes.toBytes(newrow));
            delete.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("fans"));
            table.delete(newdelete);

            table.close();


        }




    }
}
