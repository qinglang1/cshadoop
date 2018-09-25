package com.oldboy.gezhongtest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class One2Many {
    @Test
    public void  testOne2Many() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
        User u = (User) sess.selectOne("users.selectById", 2);
        u.getOrders();
        sess.commit();
        sess.close();
        System.out.println();
    }
@Test
    public void  insertOrderItem() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
        Order o = new Order();
        o.setId(11);
        Orderitem oi = new Orderitem();
        oi.setOrder(o);
        sess.insert("orderitems.insert",oi);
        sess.commit();
        sess.close();
    }
    @Test
    public void testSelectOneOrder() throws IOException{

        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
       // Order o1 = new Order();
       // o1.setId(3);
       // Order o =sess.selectOne("orders.selectOne",o1);
        //或   两种查询都可以
        Order o =sess.selectOne("orders.selectOne",11);

        sess.commit();
        sess.close();

    }


    @Test
    public void testSelectOneOrderitem() throws IOException{

        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();

        Orderitem oi =sess.selectOne("orderitems.selectOne",4);

        sess.commit();
        sess.close();

    }


    @Test
    public void testSelectOneUser() throws IOException{

        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();

        User u =sess.selectOne("users.selectOne",1);

        sess.commit();
        sess.close();

    }

}
