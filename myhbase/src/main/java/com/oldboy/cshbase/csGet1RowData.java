package com.oldboy.cshbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


/**
 *
 * row1面有多个cell,分别是id,name,age
 *
 */


public class csGet1RowData {
  @Test
  public void getyihangdata() throws Exception {

      Configuration conf = HBaseConfiguration.create();
      Connection conn = ConnectionFactory.createConnection(conf);
      Table table = conn.getTable(TableName.valueOf("test:t1"));

      Get get = new Get(Bytes.toBytes("row1"));
      Result result = table.get(get);
      List<Cell> cells = result.listCells();

      for (Cell cell : cells) {
          String row = Bytes.toString(CellUtil.cloneRow(cell));
          String cf = Bytes.toString(CellUtil.cloneFamily(cell));
          String col = Bytes.toString(CellUtil.cloneQualifier(cell));
          String value = Bytes.toString(CellUtil.cloneValue(cell));
          System.out.println(row+"/"+cf+"/"+col+"/"+value);
      }




  }


}
