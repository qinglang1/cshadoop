import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class testparsejson {
    public static String driver="com.mysql.jdbc.Driver";
    public static String user="root";
    public static String password="root";
    public static String url="jdbc:mysql://s101:3306/qinglang";



    public static   ArrayList<String> list = new ArrayList<String>();

@Test
    public void ziduan() throws Exception {
     String[] keywords={"appBaseLog","appErrorLogs","appEventLogs","appPageLogs","appStartupLogs","appUsageLogs"};


    Class.forName(driver);
    Connection conn = DriverManager.getConnection(url, user, password);
    Statement st = conn.createStatement();
    String sql = "select * from " + "table_shadow " + "where tablename =" + "'" + keywords[2] + "'";
    ResultSet rs = st.executeQuery(sql);

    ResultSetMetaData rsm = rs.getMetaData();
    int columnCount = rsm.getColumnCount();
    System.out.println(columnCount);

    rs.next();   //为什么要next，问老师；


    for (int i = 2; i <= columnCount; i++) {

        String keyword = rs.getString(i);
         if (keyword!=null){
        list.add(keyword);
        System.out.println(keyword);
         }

    }

    rs.close();
    st.close();
    conn.close();

}







    public static void parsejson(String json){

        String newjson = json.replaceAll("\\\\", "");
        JSONObject jo = JSON.parseObject(newjson);
        Object appChannel = jo.get("appChannel");
        //System.out.println(appChannel);




    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("d:/ceshi/logagg/2018-07-01.log"));

        String line=null;
        while((line=br.readLine())!=null){
            String[] splits = line.split("#");
            String json = splits[4];
            System.out.println(json);
             parsejson(json);
        }
    }
}
