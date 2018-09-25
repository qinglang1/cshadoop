package com.oldboy.gezhongtest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestCRUD {

    @Test
    public  void  testInsert() throws IOException {
        //加载配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        //session 会议
        //创建会话工厂(builder模式)
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        //开启会话，相当于连接
        SqlSession sess = sf.openSession();

        User u = new User();
       // u.setId(1);
        u.setName("zhenzidan");
        u.setAge(42);
        sess.insert("users.insert",u);
        sess.commit();
        sess.close();
        System.out.println("ok");
        
    }
@Test
    public void  testUpdate() throws IOException {
        //加载配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        //session 会议
        //创建会话工厂(builder模式)
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        //开启会话，相当于连接
        SqlSession sess = sf.openSession();
        User u = new User();
        u.setName("lixiaolong");
        u.setAge(88);
        u.setId(1);
        sess.update("users.update",u);
        sess.commit();
        sess.close();
    System.out.println("ok");


    }

    /**
     * <select id="selectAll" resultType="_User">
     *         select  * from users
     *
     *
     *其中resultType 为返回的元素的类型或返回的集合中每个元素的类型；
     */
    @Test
    public  void  testSelectOne() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
        //其中1为id
        Object o = sess.selectOne("users.selectById", 1);
        User u = (User) o;
        sess.commit();
        System.out.println(u.getName());
        sess.close();

    }

    @Test
    public void  testSelectAll() throws IOException {
        //加载配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        //session 会议
        //创建会话工厂(builder模式)
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        //开启会话，相当于连接
        SqlSession sess = sf.openSession();

        List<User> users = sess.selectList("users.selectAll");
        sess.commit();
        sess.close();
        System.out.println(users.size());
        System.out.println("ok");

    }

    @Test
    public void  testdeleteById() throws IOException {
        //加载配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        //session 会议
        //创建会话工厂(builder模式)
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        //开启会话，相当于连接
        SqlSession sess = sf.openSession();
        //4为
        sess.delete("users.delete",4);
        sess.commit();
        sess.close();
        System.out.println("ok");

    }







}
