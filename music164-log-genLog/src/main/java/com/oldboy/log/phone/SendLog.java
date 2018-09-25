package com.oldboy.log.phone;

import com.oldboy.log.util.GenLogAggUtil;

import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.text.DecimalFormat;
        import java.util.Random;

public class SendLog {

    public static void main(String[] args) throws Exception {

        Random r = new Random();

        //格式化数字串
        DecimalFormat df = new DecimalFormat("000000");

        //发送的日志个数
        for (; ;) {
            String deviceId = "Device"+ df.format(r.nextInt(100)+1);

            //genLogAgg(String deviceID)生成包含deviceId，appPlatform等及所有日志类型信息的json串
            String s = GenLogAggUtil.genLogAgg(deviceId);

            String strUrl = "http://192.168.13.25:8089/index.html";
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //定义post请求类型
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-Type","application/json");
            conn.setRequestProperty("client_time",System.currentTimeMillis()+"");
            //允许输出到服务器
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            //将json串发送至服务器
            os.write(s.getBytes());
            os.close();
            System.out.println(conn.getResponseCode());

        }



    }



}
