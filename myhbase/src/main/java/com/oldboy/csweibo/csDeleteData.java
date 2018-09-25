package com.oldboy.csweibo;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;

public class csDeleteData {

    public static void deletefi (String fans,String idol) {

        try {

            HBaseConfiguration conf = new HBaseConfiguration();
            Connection conn = ConnectionFactory.createConnection(conf);
            Table table = conn.getTable(TableName.valueOf("weibo1:guanzhu"));

            Scan scan = new Scan();

            RowFilter filter1 = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(fans+ ","));

            ValueFilter filter2 = new ValueFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(idol)));

            FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,filter1, filter2);

            scan.setFilter(filterList);

            ResultScanner scanner = table.getScanner(scan);

            for (Result result : scanner) {
                List<Cell> cells = result.listCells();
                Cell cell = cells.get(0);

                byte[] row = CellUtil.cloneRow(cell);
                Delete delete = new Delete(row);
                table.delete(delete);


            }

            table.close();
            conn.close();





        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {


        deletefi("a1", "b");


    }




}
