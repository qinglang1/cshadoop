package com.oldboy.log.etl;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Description(
        name = "getYesterday",
        value = "this is a getYesterday function",
        extended = "eg: select getYesterday(current_date()) => 2018-08-30"
)
public class GetYesterday extends UDF{

    public String evaluate(String current_date) {

        //String s = server_time.replaceAll("\\.", "");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(current_date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DATE,-1);
            Date date = calendar.getTime();
            String str = sdf.format(date);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        String s = new GetYesterday().evaluate("2018-09-32");
        System.out.println(s);
    }

}
