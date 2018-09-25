package com.oldboy.mr.userdraw.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * #分隔符
 * separator=\\|
 * time=11
 * duration=12
 * appTab=D:/userdraw/yonghuhuaxiang/appTab.txt
 * filesystem=file:///#手机号
 * phone=0
 * appid=15
 *
 */

public class ReadAppTab {
    static ConfUtil confUtil = new ConfUtil();

    public static Map<String, String> tabMap = new HashMap<String, String>();


    public static Map<String, String> readFile() {

        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", confUtil.filesystem);

            FileSystem fs = FileSystem.get(conf);

            Path p = new Path(confUtil.appTab);

            FSDataInputStream fis = fs.open(p);

            String line = null;

            while ((line = fis.readLine()) != null) {
                String[] arr = line.split(confUtil.separator);

                //arr[0]为appid
                tabMap.put(arr[0], line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tabMap;
    }
}
