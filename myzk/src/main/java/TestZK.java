
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestZK {
//列出指定结点的子结点
@Test
    public void testLs() throws Exception {
        String conn ="s102:2181,s103:2181,s104:2181";
        ZooKeeper zk=new ZooKeeper(conn,5000,null);
        List<String> children = zk.getChildren("/", false);

        for (String child :children){
            System.out.println(child);
        }

    }

    //获得指定结点的数据
@Test
    public void  testGet() throws Exception {
        Stat stat = new Stat();
        System.out.println("第一次"+stat.toString());
      String conn="s102:2181,s103:2181,s104:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);
        byte[] data = zk.getData("/a", false, stat);
        System.out.println(new String(data));
        System.out.println("第二次"+stat.toString());

    }

    //创建指定结点
@Test
    public void testCreate( ) throws Exception {
        String conn="s102:2181,s103:2181,s104:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);
        String s = zk.create("/b", "helloworld".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
        zk.close();

    }

    //删除
@Test
    public void testDelete() throws Exception {
    String conn ="s102:2181,s103:2181,s104:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);
        zk.delete("/d",-1);
        zk.close();

    }



    //set
   @Test
    public void testSet() throws Exception {
        String conn ="s102:2181,s103:2181,s104:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);
        Stat stat = zk.setData("/c", "xiaopingnihao".getBytes(), -1);
        System.out.println(stat.toString());
        zk.close();

    }

//递归列出指定结点下的子节点和数据
//   @Test
//    public static void testdiguiliechu(String path) throws Exception {
//
//        String conn ="s102:2181,s103:2181,s104:2181";
//        ZooKeeper zk = new ZooKeeper(conn, 5000, null);
//       Stat stat = new Stat();
//       List<String> childrens = zk.getChildren(path, false);
//       byte[] data = zk.getData(path, false, stat);
//       System.out.println(path+"下的数据:"+new String(data));
//       if ("/".equals(path)){
//
//        for (String child : childrens) {
//            System.out.print(path+child+",");
//
//
//        }
//
//           System.out.println();
//           for (String child : childrens) {
//
//               testdiguiliechu(path+child);
//           }
//
//    }else{
//           for (String child : childrens) {
//               System.out.print(path+"/"+child+",");
//
//
//           }
//           System.out.println();
//
//           for (String child : childrens) {
//
//               testdiguiliechu(path+"/"+child);
//           }
//
//       }
//
//
//    }
//
//    public static void main(String[] args) throws Exception {
//       testdiguiliechu("/hadoop-ha/mycluster/ActiveBreadCrumb");
//    }





    //观察者模式1  //只能观察到一次变化，因为回调wacher 的process方法之后，wacher就被杀死了，所以不能循环观察

    @Test
    public void testWatch() throws Exception {
        Stat stat = new Stat();
        String conn="s102:2181,s103:2181,s104:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType().toString());
            }
        });

        byte[] data = zk.getData("/c", true, stat);
        System.out.println(new String(data));

        while(true){

            Thread.sleep(500);
        }


    }


    //观察者模式2  回调指向的是wacher,不是process,所以不能观察到"/c"的变化

    @Test
    public void testWatch2() throws Exception {

        Stat stat = new Stat();

        String conn = "s102:2181,s103:2181,s104:2181";


        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        byte[] data = zk.getData("/c", new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                watchedEvent.getType().toString();
            }
        }, stat);

        System.out.println(new String(data));

        while (true){

            Thread.sleep(500);
        }
    }



}
