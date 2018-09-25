package com.oldboy.hive.udtf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ParseJsonUtil {
     //对一行json进行处理，生成包含多条appeventlog信息的list
    public static List<Object[]> testParse(String json) {

        String[] arr = {"logType", "createdAtMs", "musicID", "eventId", "mark"};

        List<Object[]> list = new ArrayList<Object[]>();

        String newJson = json.replaceAll("\\\\", "");

        JSONObject jo = JSON.parseObject(newJson);
        //解析deviceid
        String id = jo.get("deviceId").toString();

        //解析eventlog
        JSONArray jArray = jo.getJSONArray("appEventLogs");

        for (Object obj : jArray) {
            Object[] objects = new Object[6];
            JSONObject jo2 = (JSONObject) obj;
            objects[0] = id;

            for (int i = 0; i < arr.length; i++) {
                objects[i + 1] = jo2.get(arr[i]);

            }
            //一条appeventlog信息对应一个object数组
            list.add(objects);
        }
        return list;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("D:/2018-07-01.log"));

        String line = null;

        while ((line = br.readLine()) != null) {

            String json = line.split("\\#")[4];
            testParse(json);
        }
    }
}
