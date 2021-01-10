package com.light.article.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.light.article.service.ApArticleConfigService;
import com.light.common.constans.message.WmNewsMessageConstants;
import com.light.model.article.pojos.ApArticleConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Map;
import java.util.Optional;

/**
 * app端的文章是否下架的状态监听
 * @author houhai
 */
public class ArticleIsDownListener {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    /**
     * 监听方法
     * @param record
     */
    @KafkaListener(topics = WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC)
    public void getMessage(ConsumerRecord record){
        Optional<ConsumerRecord> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            String value = (String) record.value();
            Map map = JSON.parseObject(value, Map.class);
            Long articleId = (Long) map.get("articleId");
            Integer enable = (Integer) map.get("enable");

            LambdaUpdateWrapper<ApArticleConfig> updateWrapper= Wrappers.lambdaUpdate();
            updateWrapper.eq(ApArticleConfig::getArticleId,articleId);
            //如果enable=1表示wm_news表上架状态
            if(enable==1){
                //修改isdown=0表示上架状态
                updateWrapper.set(ApArticleConfig::getIsDown,0);
            }else{
                //如果enable=0表示wm_news表下架状态
                //修改isdown=1表示下架架状态
                updateWrapper.set(ApArticleConfig::getIsDown,1);
            }
            apArticleConfigService.update(updateWrapper);

        }
    }

}
