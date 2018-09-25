package com.oldboy.mr.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class cstestjson {
    @Test
    public void textjson(){
        String text = "{\"name\":\"tomas\"}";
        //将json转换为JSONObject的格式；
        JSONObject jo = JSON.parseObject(text);
        //通过key获取value
        Object name = jo.get("name");
        System.out.println(name);
        String name1 = jo.getString("name");
        System.out.println(name1);
        Object name2 = jo.get("name");
        System.out.println((String) name2);

    }
    @Test
    /**
     * JSONobject是Map<String,Object>的实现子类，JSONArray是List<Object>的实现子类
     * parseObject(String text)括号内必须是String格式
     * getJSONArray(String key)括号内必须是String格式
     *
     */
    public void testtomwife(){

        String line = "{\"person\":[{\"name\":\"tomas\",\"wife\":[\"marry\",\"jenny\"],\"age\":20},{\"name\":\"tom\",\"wife\":[\"marry\",\"kaili\"],\"age\":20}]}";
       JSONObject jo = JSON.parseObject(line);
    //    JSONObject jo = (JSONObject)line;         //报错 java.lang.String无法转换为com.alibaba.fastjson.JSONObject
        //       Object person = jo.get("person");
//        System.out.println(person);  //[{"wife":["marry","jenny"],"name":"tomas","age":20},{"wife":["marry","jenny"],"name":"tom","age":20}]
        JSONArray array = jo.getJSONArray("person");
        //对value组进行迭代，获取每一个value对应的kv组
        for (Object o : array) {
            //object ---string---JSONObject
          //  JSONObject jo2 = JSON.parseObject(o.toString());
            JSONObject jo2 = (JSONObject) o;   //将json的object类型直接强转成map类型
            if(jo2.get("name").equals("tom")){
                JSONArray wife = jo2.getJSONArray("wife");
                for (Object o1 : wife) {
                    System.out.println(o1);

                }
            }


        }

    }


}
