package com.light.kafka.simple;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * 消息生产者
 * @author houhai
 */
public class ProducerFastStart {

    private static final String TOPIC = "houhai";

    public static void main(String[] args) {

        //添加kafka的配置信息
        Properties properties = new Properties();

        //配置broker的信息
        properties.put("bootstrap.servers","192.168.86.130:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.RETRIES_CONFIG,10);

        //生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //封装消息
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "00001", "hello kafka");

        try {
            //发送消息
           /* RecordMetadata metadata = producer.send(record).get();
            System.out.println(metadata.offset());*/
            producer.send(record, new Callback() {
               @Override
               public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                   if (e != null) {
                       e.printStackTrace();
                   }
                   System.out.println(recordMetadata);
               }
           });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //关闭消息通道
        producer.close();

    }

}
