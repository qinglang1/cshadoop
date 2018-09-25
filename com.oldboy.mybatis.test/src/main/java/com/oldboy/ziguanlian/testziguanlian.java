package com.oldboy.ziguanlian;

import com.oldboy.gezhongtest.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 *自关联：自己的某个字段的外键是自己的主键
 *
 */
public class testziguanlian {
@Test
    public  void  testInsertziguanlian() throws IOException {

        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();
        //创建对象
        Area a1 = new Area("全国");
        Area a2 = new Area("江苏");
        Area a3 = new Area("浙江");
        Area a4 = new Area("南京");
        Area a5 = new Area("苏州");
        Area a6 = new Area("杭州");
        Area a7 = new Area("绍兴");
        //设置相互关联关系
        a1.addchildareas(a2,a3);
        a2.addchildareas(a4,a5);
        a3.addchildareas(a6,a7);
        /**
         *  插入values(#{areaname},#{parentarea.id})
         *  要按照先插入parentarea的顺序来插入area，因为只有先插入了parentarea，parentarea对象中才能返回一个自动生成的主键id，这样插入下级area的时候，才能获取到parentarea的id
         */
        sess.insert("areas.insert",a1);
        sess.insert("areas.insert",a2);
        sess.insert("areas.insert",a3);
        sess.insert("areas.insert",a4);
        sess.insert("areas.insert",a5);
        sess.insert("areas.insert",a6);
        sess.insert("areas.insert",a7);


        sess.commit();
        sess.close();

    }



    @Test
    public void testSelectfromtop() throws IOException{

        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
        SqlSession sess = sf.openSession();

        Area a=sess.selectOne("areas.selectfromtop",2);

        sess.commit();
        sess.close();

    }

}
