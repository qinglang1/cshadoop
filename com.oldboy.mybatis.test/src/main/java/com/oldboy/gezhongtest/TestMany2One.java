package com.oldboy.gezhongtest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class TestMany2One{
    @Test
    public void  testInsert() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
        User u = new User();
        //指定user的id,前提是id为8的用户必须先存在，因为从表添加条目受外键约束。
        // 指定user的id，目的的是为将某订单关联到某用户
        u.setId(2);
        Order o = new Order();
        o.setOrderNo("no009");
        o.setPrice(1200);
        o.setUser(u);
        sess.insert("orders.insert",o);
        sess.commit();
        sess.close();
    }

    /**
     * 插入新用户新订单
     *
     *
     */
    @Test
    public void  testInsert2() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
        User u = new User();
        //在插入u之前 先设置 useGeneratedKeys="true" 设置是否使用users表中自动生成的主键为true keyProperty="id" 指定自动生成的主键的字段为id
        //若不设置 useGeneratedKeys="true"  keyProperty="id"  则插入的user对象之后 user对象的id仍然是null,则后期插入的order对象关联的user.id也是null;即该订单没有顾客；
        //若设置 useGeneratedKeys="true"  keyProperty="id"  则插入的user对象之后 user对象的id则为自动生成的主键id值,则后期插入的order对象关联的user.id则为自动生成的主键id值;即该订单属于该主键id值的顾客；
        sess.insert("users.insert",u);
        Order o = new Order();
        o.setOrderNo("no0011");
        o.setPrice(1300);
        o.setUser(u);
        sess.insert("orders.insert",o);
        sess.commit();
        sess.close();
    }


    @Test
    public void  testSelectOne() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
         Order o=sess.selectOne("orders.selectOne",3);
        sess.commit();
        sess.close();
    }








}
