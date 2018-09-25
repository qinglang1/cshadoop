package com.oldboy.cshbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class csTestFilter {


    /**
     * 插入数据,其中rowkey定长
     */

    @Test
    public void putDatayouhua3() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        HTable table = (HTable) conn.getTable(TableName.valueOf("test:t1"));

        LinkedList<Put> list = new LinkedList<Put>();

        DecimalFormat decimalformat = new DecimalFormat("000");

        long start = System.currentTimeMillis();

        for (int i = 0; i <1000 ; i++) {
            String str = decimalformat.format(i);

            Put put = new Put(Bytes.toBytes("row"+str));

            put.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("name"), Bytes.toBytes("jerry"+str));

            put.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("id"), Bytes.toBytes(str));

            put.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("age"), Bytes.toBytes(decimalformat.format(i%35)+""));

            list.add(put);

        }


        table.put(list);

        table.flushCommits();

        System.out.println(System.currentTimeMillis() - start);


        table.close();

        conn.close();


    }












    /**
     *
     * 获取指定行数数据  第一行到第十行
     */
    @Test
    public void ScanData1() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("test:t1"));
        Scan scan = new Scan();

        RowFilter filter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row010")));
        scan.setFilter(filter);
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
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


    /**
     * 获得行id中含有9的所有行 subsstring
     *
     */
    @Test
    public void ScanData() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("test:t1"));
        Scan scan = new Scan();

        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("9"));
        scan.setFilter(filter);
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
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




    /**
     * 获得行id中含有9的所有行 正则
     *
     */
    @Test
    public void ScanData3() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("test:t1"));
        Scan scan = new Scan();
        //   new RegexStringComparator(".*9.*"));  不懂
        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(".*9.*"));
        scan.setFilter(filter);
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
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





    /**
     * 获得f1列族数据
     *
     */
    @Test
    public void ScanData4() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("test:t1"));
        Scan scan = new Scan();

        FamilyFilter filter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("f1"));
        scan.setFilter(filter);
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
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


    /**
     * 只显示所有name数据
     *
     * 列限定符过滤器
     *
     */
    @Test
    public void ScanData5() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("test:t1"));
        Scan scan = new Scan();
         //两种比较方法结果一样
        //QualifierFilter filter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("name"));

        QualifierFilter filter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("name")));


        scan.setFilter(filter);
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
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


    /**
     *列出row005-row020的数据
     *
     */
    @Test
    public void ScanData7() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("test:t1"));
        Scan scan = new Scan();
        RowFilter filter1 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row005")));
        RowFilter filter2 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row020")));
        FilterList filterList = new FilterList();
        filterList.addFilter(filter1);
        filterList.addFilter(filter2);
        scan.setFilter(filterList);
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
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



    /**
     *列出小于row005或大于row020的数据
     *
     */
    @Test
    public void ScanData72() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("test:t1"));
        Scan scan = new Scan();
        RowFilter filter1 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row020")));
        RowFilter filter2 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row005")));


        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE, filter1, filter2);//重要
        scan.setFilter(filterList);
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
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









    /**
     * 年龄小于30岁的所有人信息
     *值过滤器，用二进制比较
     *列限定符过滤器
     *
     *按行显示，只显示每行中列族成员age<030的列族中的age信息
     *
     */
    @Test
    public void scanData8() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("test:t1"));

        Scan scan = new Scan();


        //值过滤器，用二进制比较
        ValueFilter filter = new ValueFilter(CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("030")));

        //列限定符过滤器
        QualifierFilter filter2 = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("age")));

        FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter,filter2);

        scan.setFilter(fl);

        ResultScanner scanner = table.getScanner(scan);

        for (Result rs : scanner) {
            List<Cell> cells = rs.listCells();

            for (Cell cell : cells) {
                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                String col = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(row + "/" + cf + "/" + col + "/" + value);

            }
        }
    }

    /**
     * 年龄小于30岁的所有人信息
     *单列值过滤器，过滤的是具体某一列族中的age.如f1的单列值过滤器过滤的就是f1中的age,f2中的age将不会被过滤，scan的返回值是一整行的数据，
     * 包括被过滤的f1中的所有数据和未被过滤的f2中的所有数据。
     *按行显示，显示每行中列族成员age<030的列族的所有列族成员信息
     */
    @Test
    public void scanData9() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("test:t1"));

        Scan scan = new Scan();


        //单列值过滤器，用二进制比较
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),Bytes.toBytes("age"),
                CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("030")));

        FamilyFilter filter1 = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("f1")));

        FilterList fl1 = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter,filter1);

        //单列值过滤器，用二进制比较
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("f2"),Bytes.toBytes("age"),
                CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("030")));

        FamilyFilter filter3 = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("f2")));

        FilterList fl2 = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter2,filter3);

        FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ONE, fl1,fl2);

        scan.setFilter(fl);

        ResultScanner scanner = table.getScanner(scan);

        for (Result rs : scanner) {
            List<Cell> cells = rs.listCells();

            for (Cell cell : cells) {
                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                String col = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(row + "/" + cf + "/" + col + "/" + value);

            }
        }
    }



    /**
     * 自己做的
     *
     * 年龄小于30岁的所有人信息
     *单列值过滤器，过滤的是具体某一列族中的age.如f1的单列值过滤器过滤的就是f1中的age,f2中的age将不会被过滤，返回值是一整行的数据，
     * 包括被过滤的f1中的所有数据和未被过滤的f2中的所有数据。
     *按行显示，显示每行中列族成员age<030的列族的所有列族成员信息
     */
    @Test
    public void scanData92() throws Exception {

        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("test:t1"));

        Scan scan = new Scan();

        SingleColumnValueFilter filter11 = new SingleColumnValueFilter(Bytes.toBytes("f1"), Bytes.toBytes("age"),
                CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("030")));

        FamilyFilter filter12 = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("f1")));

        FilterList filterList1 = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter11, filter12);

        SingleColumnValueFilter filter21 = new SingleColumnValueFilter(Bytes.toBytes("f2"), Bytes.toBytes("age"),
                CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("030")));

        FamilyFilter filter22 = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("f2")));

        FilterList filterList2 = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter21, filter22);

        FilterList filterListtotal = new FilterList(FilterList.Operator.MUST_PASS_ONE,filterList1,filterList2);

        scan.setFilter(filterListtotal);

        ResultScanner scanner = table.getScanner(scan);

        for (Result rs : scanner) {
            List<Cell> cells = rs.listCells();

            for (Cell cell : cells) {
                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                String col = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(row + "/" + cf + "/" + col + "/" + value);

            }
        }
    }






}
