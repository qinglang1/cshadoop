package com.oldboy.hive.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 将{\"appChannel\":\"anroid bus\",\"appErrorLogs\":[{\"createdAtMs\":1530457560000,\"errorBrief\":\"at cn.lift.appIn.control.CommandUtil.getInfo(CommandUtil.java:67)\",\"errorDetail\":\"at cn.lift.dfdfdf.control.CommandUtil.getInfo(CommandUtil.java:67) at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) at java.lang.reflect.Method.invoke(Method.java:606)\"}],\"appEventLogs\":[{\"createdAtMs\":1530410880000,\"eventId\":\"null\",\"logType\":\"event\",\"mark\":\"0\",\"musicID\":\"白日梦蓝\"},{\"createdAtMs\":1530444360000,\"duration\":\"00:20\",\"eventId\":\"skip\",\"logType\":\"event\",\"mark\":\"-1\",\"musicID\":\"像艳遇一样忧伤\",\"playTime\":\"1530444360000\"}],\"appPageLogs\":[{\"createdAtMs\":1530439440000,\"logType\":\"page\",\"nextPage\":\"main.html\",\"pageId\":\"list.html\",\"pageViewCntInSession\":0,\"visitIndex\":\"1\"},{\"createdAtMs\":1530397980000,\"logType\":\"page\",\"nextPage\":\"test.html\",\"pageId\":\"main.html\",\"pageViewCntInSession\":0,\"visitIndex\":\"0\"}],\"appPlatform\":\"blackberry\",\"appStartupLogs\":[{\"brand\":\"小米\",\"carrier\":\"中国联通\",\"country\":\"america\",\"createdAtMs\":1530383520000,\"logType\":\"startup\",\"network\":\"4g\",\"province\":\"guangxi\",\"screenSize\":\"480 * 320\"},{\"brand\":\"华为\",\"carrier\":\"中国铁通\",\"country\":\"china\",\"createdAtMs\":1530412020000,\"logType\":\"startup\",\"network\":\"cell\",\"province\":\"guangdong\",\"screenSize\":\"1136 * 640\"}],\"appUsageLogs\":[{\"createdAtMs\":1530407520000,\"logType\":\"usage\",\"singleDownloadTraffic\":\"45900\",\"singleUploadTraffic\":\"128\",\"singleUseDurationSecs\":\"45\"},{\"createdAtMs\":1530402060000,\"logType\":\"usage\",\"singleDownloadTraffic\":\"12800\",\"singleUploadTraffic\":\"459\",\"singleUseDurationSecs\":\"45\"}],\"appVersion\":\"2.0.0\",\"deviceId\":\"Device000099\",\"deviceStyle\":\"iphone 6\",\"osType\":\"1.2.0\"}进行解析
 *
 */


public class ParseEvent extends GenericUDTF {

    PrimitiveObjectInspector poi;

    //定义了表结构
    //argOIs => 输入字段的字段类型
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {

        if (argOIs.getAllStructFieldRefs().size() != 1) {
            throw new UDFArgumentException("参数个数只能为1");
        }

        //如果输入字段类型非String，则抛异常
        ObjectInspector oi = argOIs.getAllStructFieldRefs().get(0).getFieldObjectInspector();

        if (oi.getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentException("参数非基本类型,需要基本类型string");

        }
        //强转为基本类型对象检查器
        poi = (PrimitiveObjectInspector) oi;
        if (poi.getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("参数非string,需要基本类型string");
        }

        //构造字段名,word
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("deviceId");//第一个字段
        fieldNames.add("logType");//第二个字段
        fieldNames.add("createdAtMs");
        fieldNames.add("musicID");
        fieldNames.add("eventId");
        fieldNames.add("mark");



        //构造字段类型,string
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
        //通过基本数据类型工厂获取java基本类型oi
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);


        //构造对象检查器
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,
                fieldOIs);
    }


    /**
     * 生成数据阶段
     *
     * 	create table logAgg(serverTime string,remoteIp string,clientTime string,status string, json string) partitioned by(year string, month string, day string)row format delimited fields terminated by '#' ;
     *
     *  select parseEvent(json) from logAgg    其中json为表格loggg中的一个字段
     *
     *  process(Object[] args)  1.其中args为parseEvent(json)括号中的参数，因为 parseEvent(json) 括号中只有一个参数，
     *                          所以args只有一个元素args[0];
     *                          2.一次对一行json进行处理
     *
     *
     *
     */
    //
    @Override
    public void process(Object[] args) throws HiveException {


        //得到一行数据
        String json = (String) poi.getPrimitiveJavaObject(args[0]);

        List<Object[]> list = ParseJsonUtil.testParse(json);

        for (Object[] objects : list) {
            forward(objects);    //forward(Object[] obj)一次则产生表格中的一行数据
                                 //参数是Object类型的数组，objects中的每一个元素都是表格中对应的字段值
        }

    }


    //do nothing
    @Override
    public void close() throws HiveException {

    }
}
