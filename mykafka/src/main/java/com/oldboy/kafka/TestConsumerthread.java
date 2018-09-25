package com.oldboy.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TestConsumerthread {
    public static void main(String[] args) {

        //构造java配置文件
        Properties prop = new Properties();
        prop.put("zookeeper.connect", "s102:2181,s103:2181,s104:2181");
        prop.put("group.id", "g2");
        //通过java配置文件初始化consumer配置文件
        ConsumerConfig config = new ConsumerConfig(prop);

        //通过consumer的配置文件，创建java消费者连接器
        ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);

        //String：主题
        //int: 消费线程数
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put("t2", 2);

        //通过java消费者连接器，消费者通过3个线程，获取topic "t1"的消息流
        //String:主题
        //list：kafka消息流
        Map<String, List<KafkaStream<byte[], byte[]>>> message = connector.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streamList = message.get("t1");
        //迭代list
        for (final KafkaStream<byte[], byte[]> stream : streamList) {
            new Thread() {
                @Override
                public void run() {
                    //获取迭代器
                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
                    //通过while迭代所有数据
                    while (it.hasNext()) {
                        MessageAndMetadata<byte[], byte[]> next = it.next();
                        System.out.println(Thread.currentThread().getName() /** + "\tkey:" + new String(next.key())**/+ "\tmessage:" + new String(next.message()));
                    }
                }
            }.start();

        }
    }
}
