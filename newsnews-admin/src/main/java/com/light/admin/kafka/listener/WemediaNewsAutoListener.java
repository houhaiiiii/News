package com.light.admin.kafka.listener;

import com.light.admin.service.WemediaNewsAutoScanService;
import com.light.common.constants.message.NewsAutoScanConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * kafka消费者监听
 * 自媒体文章发布以后会发消息过来自动进行审核，需要在admin端来接收消息，处理审核
 * @author houhai
 */
@Component
public class WemediaNewsAutoListener {

    @Autowired
    WemediaNewsAutoScanService wemediaNewsAutoScanService;

    //                                            WM_NEWS_AUTO_SCAN_TOPIC
    @KafkaListener(topics = NewsAutoScanConstants.WM_NEWS_AUTO_SCAN_TOPIC)
    public void recevieMessage(ConsumerRecord<?,?> record){
        Optional<? extends ConsumerRecord<?, ?>> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            Object value = record.value();
            wemediaNewsAutoScanService.autoScanByMediaNewsId(Integer.valueOf((String) value));
        }

    }

}
