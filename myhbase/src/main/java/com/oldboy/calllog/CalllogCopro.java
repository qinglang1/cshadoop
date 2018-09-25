package com.oldboy.calllog;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class CalllogCopro extends BaseRegionObserver {

    private static final String CALLLOG_TABLE = "calllog";

    /**
     * 插入主叫normal信息同时生成被叫normal信息refID
     *
     * @param e
     * @param put
     * @param edit
     * @param durability
     * @throws IOException
     */
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);

        RegionCoprocessorEnvironment env = e.getEnvironment();

        String rowKey = new String(put.getRow());
        //事先判断插入的是否是主叫
        //防止循环插入数据
        if (rowKey.contains(",1,")) {
            String[] arr = rowKey.split(",");
            String caller = arr[1];
            String callee = arr[4];
            String time = arr[2];

            String calleeKey = GenKeyUtil.genCallee(callee, caller, time);

            Table table = (Table) env.getTable(TableName.valueOf(CALLLOG_TABLE));

            Put put2 = new Put(Bytes.toBytes(calleeKey));

            put2.addColumn(Bytes.toBytes("normal"), Bytes.toBytes("refId"), Bytes.toBytes(rowKey));

            table.put(put2);
             table.close();
        }

    }

    @Override
    public boolean postScannerNext(ObserverContext<RegionCoprocessorEnvironment> e, InternalScanner s, List<Result> results, int limit, boolean hasMore) throws IOException {
        return super.postScannerNext(e, s, results, limit, hasMore);
    }
}
