package com.oldboy.mr.userdraw;

import com.oldboy.mr.userdraw.util.ReadAppTab;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Map;

public class UserDrawMapper2 extends Mapper<LongWritable,Text,Text,Text> {
   ReadAppTab tab= new ReadAppTab();
    Map<String, String> appmap = tab.readFile();

    /**
     * +/rmMLtMV+s+gXTDoOaoxQ==|10005|824
     * +/rmMLtMV+s+gXTDoOaoxQ==|220499|98
     * +/rmMLtMV+s+gXTDoOaoxQ==|70093|75610
     *
     * AppTab
     * 10005|微信|0.001|0.001|0|0.2|0.3|0.2|0.3
     * 220499|搜狐浏览器|0.001|0.001|0.001|0.002|0.002|0.002|0.003
     * 70093|豌豆荚|0.001|0.001|0.001|0.002|0.002|0.002|0.003
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] userarr = value.toString().split("\\|");
        String phone = userarr[0];
        String appid = userarr[1];
        String sum = userarr[2];
        String appline = appmap.get(userarr[1]);
        String[] app = appline.split("\\|");
        String nan = app[2];
        String nv = app[3];
        String age1 = app[4];
        String age2 = app[5];
        String age3 = app[6];
        String age4 = app[7];
        String age5 = app[8];

        double[] quanzhong = new double[7];
        for (int i=0;i<7;i++){
            quanzhong[i]=Double.parseDouble(userarr[2])*Double.parseDouble(app[i+2]);
        }

        StringBuffer sb = new StringBuffer();
        for (double v : quanzhong) {
            sb.append(v+""+"|");
        }
        sb.substring(0,sb.length()-1);

     context.write(new Text(userarr[0]),new Text(sb.toString()));

    }
}
