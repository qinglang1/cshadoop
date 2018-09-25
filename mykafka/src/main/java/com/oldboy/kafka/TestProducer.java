package com.oldboy.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class TestProducer {
    public static void main(String[] args) throws Exception {

        //构造java配置文件
        Properties prop = new Properties();
        //生产者连接到9092端口，消费者连接到2181端口
        prop.put("metadata.broker.list","s102:9092,s103:9092,s104:9092");
        prop.put("serializer.class","kafka.serializer.StringEncoder");    //serializer 串行器   encoder 编码器

        //通过java配置文件初始化producer配置文件
        ProducerConfig config = new ProducerConfig(prop);

        //通过producer配置文件，初始化producer
        Producer<String, String> producer = new Producer<String, String>(config);

        //构造message数据(包括k-v)KeyedMessage<topic,message>
        for (int i = 1; i < 10000; i++) {
            KeyedMessage<String, String> msg = new KeyedMessage<String, String>("t2", "qinglang" + i);
            producer.send(msg);
           // Thread.sleep(500);
        }

        producer.close();
    }
}