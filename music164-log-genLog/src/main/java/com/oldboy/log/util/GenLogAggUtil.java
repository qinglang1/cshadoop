package com.oldboy.log.util;

import com.alibaba.fastjson.JSON;
import com.oldboy.log.common.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GenLogAggUtil {
    //genLogAgg(String deviceID)生成包含deviceId，appPlatform等及所有日志类型信息的json串
    public static String genLogAgg(String deviceID) {
        try {
            Map<String, List<String>> map = DictUtil.map;

            AppLogAggEntity logAgg = new AppLogAggEntity();
            //AppLogAggEntity的实例设置deviceId
          //  logAgg.setDeviceId(deviceID);     //有问题 设置deviceId的值应该放在for循环设置字段值的后面

            //将logAgg赋值,调用了本类中的genLogList()方法
            logAgg.setAppErrorLogs(genLogList(AppErrorLog.class));
            logAgg.setAppEventLogs(genLogList(AppEventLog.class));
            logAgg.setAppPageLogs(genLogList(AppPageLog.class));
            logAgg.setAppStartupLogs(genLogList(AppStartupLog.class));
            logAgg.setAppUsageLogs(genLogList(AppUsageLog.class));

            Field[] fields2 = AppLogAggEntity.class.getDeclaredFields();
            for (Field field : fields2) {
                if (field.getType() == String.class) {
                    //设置访问权限
                    field.setAccessible(true);
                    String value = DictUtil.randomValue(map, field.getName().toLowerCase());
                    field.set(logAgg, value);
                }
            }
            logAgg.setDeviceId(deviceID);
            return JSON.toJSONString(logAgg);     //将一个AppLogAggEntity类型的对象变成一个json串,有pretty形式
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //生成某日志类型实例的集合
    public static <T> List<T> genLogList(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        Random r = new Random();

        try {
            GenLogUtil logUtil = new GenLogUtil();
            //如果类为AppStartupLog，那么只需生成一个日志
            if(clazz.equals(AppStartupLog.class)){
                list.add(logUtil.genLog(clazz));
                return list;
            }
            //如果类为AppEventLog，那么需要生成多个日志
            if(clazz.equals(AppEventLog.class)){
                for(int i = 0 ; i <= r.nextInt(10) ;i++){
                    list.add(logUtil.genLog(clazz));
                }
                return list;
            }
            else {
                for(int i = 0 ; i <= r.nextInt(3) ;i++){
                    list.add(logUtil.genLog(clazz));
                }
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(genLogAgg("1"));
    }


}
