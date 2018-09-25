package com.oldboy.gezhongtest.many2many;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class testmanytomany {
    public static void main(String[] args) throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
        //创建对象
        tea t1 = new tea();
        tea t2 = new tea();
        stu s1 = new stu();
        stu s2 = new stu();
        stu s3 = new stu();
        stu s4 = new stu();
        
        //设置老师和学生的关联关系
        t1.addstuduents(s1,s2,s3);
        t2.addstuduents(s2,s3,s4);
        //向老师表中插入数据
        sess.insert("teas.insert",t1);
        sess.insert("teas.insert",t2);
        //向学生表中插入数据
        sess.insert("stus.insert",s1);
        sess.insert("stus.insert",s2);
        sess.insert("stus.insert",s3);
        sess.insert("stus.insert",s4);

        //向links表中插入老师id和学生id的对应关系
        sess.insert("teas.insertlinks",t1);
        sess.insert("teas.insertlinks",t2);
        
        sess.commit();
        sess.close();

        System.out.println();






    }
    
}
