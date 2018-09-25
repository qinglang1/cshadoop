package qqtongxin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

 private  static Properties prop;

 static {

     InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties");
     prop =new Properties();
     try {
         prop.load(in);
     } catch (IOException e) {
         e.printStackTrace();
     }

 }


 //获得String类型的属性值
 public static String getStringValue(String name){


     return  prop.getProperty(name);
 }



 //获得int类型的属性值
    public  static  int getIntValue(String name){


     return Integer.parseInt(prop.getProperty(name));
    }

//获得boolean类型的属性值
    public  static  boolean getBooleanValue(String name){

     return prop.getProperty(name).toLowerCase().equals("true");

    }

}
