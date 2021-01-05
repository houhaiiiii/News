package com.light.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.light.kafka.pojos.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 消息消费者，监听类,对象传递
 * @author houhai
 */
@Component
public class HelloListener {

    @KafkaListener(topics = "kafka-hello")
    public void receiveMessage(ConsumerRecord<?,?> record){
        Optional<? extends ConsumerRecord<?,?>> optional = Optional.ofNullable(record);
        if (optional.isPresent()){
            Object value = record.value();
            User user = JSON.parseObject(String.valueOf(value), User.class);
            System.out.println(value);
            System.out.println(user);
        }
    }

}
