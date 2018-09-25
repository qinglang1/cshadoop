package com.oldboy.hive.csudtf;

import org.apache.hadoop.hive.ql.exec.MapredContext;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;

public class MyUDTF extends GenericUDTF {
    /**
     * nest：n:鸟巢 v:嵌套
     * inspector:检查员
     * initiallize:初始化
     * StructObjectInspector:结构体对象检查器
     *
     *
     * 结构体Struct     struct<sex:string,age:int>  结构体中包含1.字段名称 2.字段类型
     *表结构包含：1.字段名称 2.字段类型
     *例： employee的表结构
     * +---------------+-----------------------------+
     * |   col_name    |          data_type          |
     * +---------------+-----------------------------+
     * | name          | string                      |
     * | work_place    | array<string>               |
     * | sex_age       | struct<sex:string,age:int>  |
     * | score         | map<string,int>             |
     * | depart_title  | map<string,string>          |
     * +---------------+-----------------------------+
     *
     * 将表结构以结构体形式对待
     *
     * ObjectInspector是一个接口，其子抽象类和interface包括：
     * 1.StructObjectInspector
     * (用来完成对struct数据类型的解析，它本身由一组ObjectInspector组成.
     * 由于Hive支持Nested Data Structure，所以，在StructObjectInspector中又可以（一层或多层的）嵌套任意的ObjectInspector.
     * Struct, Map, List, Union是Hive支持的4种集合数据类型。
     * 若某一数据被声明为Struct类型，这样解析这一数据的StructObjectInspector中就会嵌套另一个StructObjectInspector.)
     * 2.MapObjectInspector
     * 3.ListObjectInspector
     * 4.PrimitiveObjectInspector  用来完成对基本数据类型的解析
     * 5. UnionObjectInspector
     *
     *
     *initialize 构造表结构
     */
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
       return null;
    }

    /**
     * process: n:过程 v:处理
     *
     */
    @Override
    public void process(Object[] objects) throws HiveException {

    }

    @Override
    public void close() throws HiveException {

    }
}
