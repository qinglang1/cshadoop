package com.oldboy.phoenix;

import org.junit.Test;

import java.sql.*;

public class TestJDBC {

  @Test
    public void testinsert() throws Exception {
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

        String url="jdbc:phoenix:s102,s103,s104";

      Connection conn = DriverManager.getConnection(url);

      PreparedStatement ppst = conn.prepareStatement("upsert into customers values(?,?,?)");
      for (int i = 0; i < 100; i++) {
          ppst.setInt(1,i);
          ppst.setString(2,"tom"+i);
          ppst.setInt(3,i%30);
          ppst.execute();
      }

      conn.commit();
      ppst.close();
      conn.close();

  }

    @Test
    public void testselect() throws Exception {
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

        String url="jdbc:phoenix:s102,s103,s104";

        Connection conn = DriverManager.getConnection(url);

        Statement st = conn.createStatement();
//        ResultSet rs = st.executeQuery("select * from customers");
//
//        while(rs.next()){
//
//            int id = rs.getInt(1);
//            String name = rs.getString(2);
//            int age = rs.getInt(3);
//            System.out.println("id:"+id+"\t"+"name:"+name+"\t"+"age:"+age);
//
//
//        }


        ResultSet rs = st.executeQuery("select * from \"ns1\".\"t3\"");       //注意转义字符的使用

        while(rs.next()){

            String id = rs.getString(1);
            String name = rs.getString(2);
            String age = rs.getString(3);
            System.out.println("id:"+id+"\t"+"name:"+name+"\t"+"age:"+age);


        }



        st.close();
        conn.close();

    }

}
