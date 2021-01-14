package com.light.kafka.simple;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

/**
 * 需求：
 * 接收kafka消息内容并计算消息内单词的个数
 * @author houhai
 *      hello kafka stareams
 *      hello heima kafka
 *      hello beijing heima kafka
 */
public class KafkaStreamFastStart {

    public static void main(String[] args) {
        //kafka配置信息
        Properties prop = new Properties();
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.86.130:9092");
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG,"article_behavior_count");
        prop.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        //创建kafkaStream对象
        KafkaStreams kafkaStreams = new KafkaStreams(null,prop);
    }

}
