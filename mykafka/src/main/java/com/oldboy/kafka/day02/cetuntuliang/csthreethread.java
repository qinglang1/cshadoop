package com.oldboy.kafka.day02.cetuntuliang;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Properties;

public class csthreethread {
    @Test
    public void test5(){

        Thread t1 = new Thread()  {
            @Override
            public void run() {
                try {
                    Properties props = new Properties();
                    props.put("bootstrap.servers","s102:9092,s103:9092,s104:9092");
                    props.put("acks","0");
                    props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
                    props.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");

                    long start = System.currentTimeMillis();
                    KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);
                    for (int i = 0; i <1024*342 ; i++) {
                        ProducerRecord<String, byte[]> record = new ProducerRecord<>("t14", new byte[1024]);
                        producer.send(record);

                    }

                    System.out.println(System.currentTimeMillis()-start);
                    producer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        Thread t2 = new Thread()  {
            @Override
            public void run() {
                try {
                    Properties props = new Properties();
                    props.put("bootstrap.servers","s102:9092,s103:9092,s104:9092");
                    props.put("acks","0");
                    props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
                    props.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");

                    long start = System.currentTimeMillis();
                    KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);
                    for (int i = 0; i <1024*342 ; i++) {
                        ProducerRecord<String, byte[]> record = new ProducerRecord<>("t14", new byte[1024]);
                        producer.send(record);

                    }

                    System.out.println(System.currentTimeMillis()-start);
                    producer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        Thread t3 = new Thread()  {
            @Override
            public void run() {
                try {
                    Properties props = new Properties();
                    props.put("bootstrap.servers","s102:9092,s103:9092,s104:9092");
                    props.put("acks","0");
                    props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
                    props.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");

                    long start = System.currentTimeMillis();
                    KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);
                    for (int i = 0; i <1024*342 ; i++) {
                        ProducerRecord<String, byte[]> record = new ProducerRecord<>("t14", new byte[1024]);
                        producer.send(record);

                    }

                    System.out.println(System.currentTimeMillis()-start);
                    producer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        t1.start();
        t2.start();
        t3.start();

        while (true){

        }

    }
}
