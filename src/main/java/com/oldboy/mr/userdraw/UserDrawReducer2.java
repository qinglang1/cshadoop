package com.oldboy.mr.userdraw;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;

public class UserDrawReducer2 extends Reducer<Text,Text,NullWritable,Text> {

    MultipleOutputs mos;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

    mos =  new MultipleOutputs(context);
    }

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        double[] sum = new double[7];
        for (Text value : values) {
            String[] quanzhongyinzi = value.toString().split("\\|");
            for (int i=0;i<7;i++ ) {

             sum[i]+= Double.parseDouble(quanzhongyinzi[i]);

            }
        }
        StringBuffer sb2 = new StringBuffer();
        for (double v : sum) {
            sb2.append(v+""+"|")  ;
        }
        sb2.substring(0,sb2.length()-1);

        mos.write("seq",key,new Text(sb2.toString()),"D:/yonghuhuaxiang/seq/");
        mos.write("text",key,new Text(sb2.toString()),"D:/yonghuhuaxiang/text/");




        double[] quanzhong = new double[7];
        quanzhong[0]=sum[0]/(sum[0]+sum[1]);
        quanzhong[1]=sum[1]/(sum[0]+sum[1]);

        for (int i=2; i<6;i++){
            quanzhong[i]=sum[i]/(sum[2]+sum[3]+sum[4]+sum[5]+sum[6]);
        }

        StringBuffer sb = new StringBuffer();
        sb.append(key.toString()+"|");
        for (double v : quanzhong) {
            sb.append(v+""+"|");
        }
        sb.substring(0,sb.length()-1);

        context.write(NullWritable.get(),new Text(sb.toString()));


    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        mos.close();
    }
}
