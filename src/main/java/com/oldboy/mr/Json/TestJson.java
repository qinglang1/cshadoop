package com.oldboy.mr.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class TestJson {

    @Test
    public void testJson() {

       String text = "{\"name\":\"tomas\"}";
       //测试转义字符\
        System.out.println(text);

        //将json转化为jsonObject格式
        JSONObject jo = JSON.parseObject(text);

        //通过key获取value
        Object name = jo.get("name");
        System.out.println(name);

    }


    @Test
    public void testJsonArray() {
        String line = "{\"person\":[{\"name\":\"tomas\",\"wife\":[\"marry\",\"jenny\"],\"age\":20},{\"name\":\"tom\",\"wife\":[\"marry\",\"jenny\"],\"age\":20}]}";

        JSONObject jo = JSON.parseObject(line);

        JSONArray jsonArray = jo.getJSONArray("person");

        for (Object object : jsonArray) {
            JSONObject jo2 = JSON.parseObject(object.toString());
            if(jo2.get("name").toString().equals("tom")){
                JSONArray jsonArray2 = jo2.getJSONArray("wife");
                for(Object obj2 : jsonArray2){
                    System.out.println(obj2);

                }
            }
        }

    }


@Test
    public  void  testwaimai(){
        String line="{\"reviewPics\":null,\"extInfoList\":[{\"title\":\"contentTags\",\"values\":[\"午餐\",\"分量适中\"],\"desc\":\"\",\"defineType\":0},{\"title\":\"tagIds\",\"values\":[\"684\",\"240\"],\"desc\":\"\",\"defineType\":0}],\"expenseList\":null,\"reviewIndexes\":[2],\"scoreList\":null}";
        JSONObject jo = JSON.parseObject(line);
        JSONArray jsonArray = jo.getJSONArray("extInfoList");
        for (Object object: jsonArray){
            JSONObject jo2 = JSON.parseObject(object.toString());
            //jo2.

        }

    }







}
