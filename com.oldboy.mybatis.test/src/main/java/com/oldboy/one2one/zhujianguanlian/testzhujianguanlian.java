package com.oldboy.one2one.zhujianguanlian;

import com.oldboy.ziguanlian.Area;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class testzhujianguanlian {
    @Test
    public  void  testInsertzhujianguanlian() throws IOException {

        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
        //创建对象
        Husband h1 = new Husband();
        Husband h2 = new Husband();
        Husband h3 = new Husband();
        h1.setHname("tom1");
        h2.setHname("tom2");
        h3.setHname("tom3");

        Wife w1 = new Wife();
        Wife w2 = new Wife();
        Wife w3 = new Wife();
        w1.setWname("marry1");
        w2.setWname("marry2");
        w3.setWname("marry3");
        //设置关联关系
        w1.setHusband(h1);
        w2.setHusband(h2);
        w3.setHusband(h3);

        //插入数据
        sess.insert("husbands.insert",h1);
        sess.insert("husbands.insert",h2);
        sess.insert("husbands.insert",h3);
        sess.insert("wifes.insert",w1);
        sess.insert("wifes.insert",w2);
        sess.insert("wifes.insert",w3);

        sess.commit();
        sess.close();

    }


    @Test
    public void testSelectOneHusband() throws IOException{

        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();

       Husband h=sess.selectOne("husbands.selectOne",3);

        sess.commit();
        sess.close();

    }




}
