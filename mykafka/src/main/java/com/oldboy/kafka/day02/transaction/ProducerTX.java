package com.oldboy.kafka.day02.transaction;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerTX {
    public static void main(String[] args)throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "s102:9092,s104:9092,s103:9092");
        props.put("transactional.id", "tx-1");      //设置事务id
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        //初始化事务
        producer.initTransactions();

        try {
            //开始事务
            producer.beginTransaction();
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>("tx", Integer.toString(i), Integer.toString(i)));
                Thread.sleep(500);
            }
            //提交事务
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // 以上三种异常，不可恢复，所以只能关闭producer
            producer.close();
        } catch (KafkaException e) {
            // 其他异常，只需终止事务并重试
            producer.abortTransaction();
        }
        producer.close();
    }
}
