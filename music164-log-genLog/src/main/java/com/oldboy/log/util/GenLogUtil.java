package com.oldboy.log.util;

import com.alibaba.fastjson.JSON;
import com.oldboy.log.common.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class GenLogUtil {
   // 未知   此处泛型的意思
    //genLog(Class<T> clazz)返回值是某日志类型实例
    public <T> T genLog(Class<T> clazz) throws Exception {
        Map<String, List<String>> map = DictUtil.map;
        //name_time 歌名和歌曲时长
        List<String> name_time = MysqlUtil.randomMusic();

        T t1 = clazz.newInstance();

        //如果日志是baseLog的子类，只需要添加createAtMs
        //t1 instanceof AppBaseLog   t1是AppBaseLog的实例
        if (t1 instanceof AppBaseLog) {
            //获取所有字段baseLog+EventLog
            Class clazz2 = AppBaseLog.class;

            Field[] fields2 = clazz2.getDeclaredFields();
            for (Field field : fields2) {
                //设置访问权限
                field.setAccessible(true);       //重要
                ((AppBaseLog) t1).setCreatedAtMs(System.currentTimeMillis());
            }
        }

        //如果t1是event日志类型，那么直接设置Event的所有字段

        if (t1 instanceof AppEventLog) {

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {  //1.此循环实际上只给eventId赋了值，因为其他字段在map中没有,返回值为null
                //设置访问权限
                field.setAccessible(true);
                //重要 AppEventLog的实例t1 获得所有字段的随机值
                //对象设置字段值，应为map里面的key都是小写，而AppEventLog中的字段每个字段里面都有大写字母，所以要把字段中的各个字符都转成小写
                String value = DictUtil.randomValue(map, field.getName().toLowerCase());    //Converts all of the characters in this {@code String} to lower
              //  System.out.println(value);
                field.set(t1, value);

            }
            //歌曲id最大值为624，歌曲总条数为619说明有几条id不存在
            if (name_time.size() != 0) {
                //2.设置歌曲名称  musicID
                ((AppEventLog) t1).setMusicID(name_time.get(0));  // musicID   歌曲名称
                //3.设置什么时刻播放   playTime
                ((AppEventLog) t1).setPlayTime(System.currentTimeMillis() + "");   //1.playTime  什么时刻播放    2.duration      播放时长
            }

            //如果eventId为听过或主动播放，则设定听歌时长
            if (((AppEventLog) t1).getEventId().equals("play") || ((AppEventLog) t1).getEventId().equals("listen")) {
                if (name_time.size() != 0) {
                    String duration = name_time.get(1);        //此处有问题， name_time.get(1)为歌曲时长，不是播放时长
                    //4.设置eventId中play和listen的duration播放时长为歌曲时长
                    ((AppEventLog) t1).setDuration(duration);
                }
            }
            if (((AppEventLog) t1).getEventId().equals("skip")) {
                if (name_time.size() != 0) {
                    String duration = name_time.get(1);  //此句可以不要
                    ////4.设置eventId中skip的duration播放时长为30秒
                    ((AppEventLog) t1).setDuration("00:30");
                }
            }

            //5.设置mark 打分
            String mark = parseMark(((AppEventLog) t1).getEventId());
            ((AppEventLog) t1).setMark(mark);

            //如果t1不是AppEventLog，则正常随机循环
        } else {
            Field[] fields2 = clazz.getDeclaredFields();
            for (Field field : fields2) {
                if (field.getType() == String.class) {
                    //设置访问权限
                    field.setAccessible(true);
                    String value = DictUtil.randomValue(map, field.getName().toLowerCase());
                    field.set(t1, value);
                }
            }
        }

        return t1;

    }
     //返回值为String类型
    public static String parseMark(String eventId) {
        switch (eventId) {
            case "share":
                return "4";
            case "favourite":
                return "3";
            case "play":
                return "2";
            case "listen":
                return "1";
            case "skip":
                return "-1";
            case "nofavourite":
                return "-4";
            case "black":
                return "-5";
            default:
                return "0";
        }
    }

//    public static void main(String[] args) throws Exception {
//        GenLogUtil genLogUtil = new GenLogUtil();
//        for (int i = 0; i < 50; i++) {
//            String s1 = genLogUtil.genLog(AppEventLog.class);
//            String s2 = genLogUtil.genLog(AppErrorLog.class);
//            String s3 = genLogUtil.genLog(AppStartupLog.class);
//            String s4 = genLogUtil.genLog(AppPageLog.class);
//            System.out.println(s1);
//            System.out.println(s2);
//            System.out.println(s3);
//            System.out.println(s4);
//            System.out.println("===============================================");
//        }
//    }
}
