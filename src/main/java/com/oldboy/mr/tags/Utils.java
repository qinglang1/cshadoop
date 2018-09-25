package com.oldboy.mr.tags;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * {}代表map, JSONObject      []代表list,JSONArray
 * JSONobject是Map<String,Object>的实现子类，JSONArray是List<Object>的实现子类
 * parseObject(String text)括号内必须是String格式
 * getJSONArray(String key)括号内必须是String格式
 *
 *
 *
 */

//86913510	{"reviewPics":null,"extInfoList":[{"title":"contentTags","values":["午餐","分量适中"],"desc":"","defineType":0},{"title":"tagIds","values":["684","240"],"desc":"","defineType":0}],"expenseList":null,"reviewIndexes":[2],"scoreList":null}
public class Utils {
    public static List<String> parsejson(String json) {
        ArrayList<String> list = new ArrayList<String>();

        JSONObject jo = JSON.parseObject(json);
        JSONArray jArray = jo.getJSONArray("extInfoList");
        if (jArray != null && jArray.size() != 0) {                 //对jArray迭代之前，先要确认jArray不为空且元素个数不为0
            for (Object o : jArray) {
                JSONObject jo2 = JSON.parseObject(o.toString());
              //  JSONObject jo2 = (JSONObject) o;                //将json的object类型直接强转成map类型
                if (jo2.get("title").toString().equals("contentTags")) {

                    JSONArray jArray2 = jo2.getJSONArray("values");
                    if (jArray2 != null && jArray2.size() != 0) {
                        for (Object o1 : jArray2) {
                            list.add(o1.toString());
                        }



                    }

                }

            }
        }
        return list;

    }
}


