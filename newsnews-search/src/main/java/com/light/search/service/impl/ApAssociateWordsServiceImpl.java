package com.light.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.search.dtos.UserSearchDto;
import com.light.model.search.pojos.ApAssociateWords;
import com.light.search.mapper.ApAssociateWordsMapper;
import com.light.search.model.Trie;
import com.light.search.service.ApAssociateWordsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联想词表 服务实现类
 * </p>
 *
 * @author houhai
 */
@Slf4j
@Service
public class ApAssociateWordsServiceImpl extends ServiceImpl<ApAssociateWordsMapper, ApAssociateWords> implements ApAssociateWordsService {

    @Override
    public ResponseResult search(UserSearchDto userSearchDto) {
        //1.检查参数
        if (userSearchDto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.模糊查询数据
        Page pageParam = new Page(0, userSearchDto.getPageSize());
        List<ApAssociateWords> list = list();
        IPage page = page(pageParam, Wrappers.<ApAssociateWords>lambdaQuery().like(ApAssociateWords::getAssociateWords, userSearchDto.getSearchWords()));

        return ResponseResult.okResult(page.getRecords());
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseResult searchV2(UserSearchDto dto) {
        //1.从缓存中获取数据
        String assoStr = redisTemplate.opsForValue().get("associate_list");
        List<ApAssociateWords> apAssociateWords = null;
        if(StringUtils.isNotEmpty(assoStr)){
            //2.缓存中存在，直接拿数据
            apAssociateWords = JSON.parseArray(assoStr, ApAssociateWords.class);
        }else {
            //3.缓存中不存在，从数据库中获取数据，存储到redis
            apAssociateWords = list();
            redisTemplate.opsForValue().set("associate_list", JSON.toJSONString(apAssociateWords));
        }
        //4.构建trie数据结构，从trie中获取数据，封装返回
        Trie t = new Trie();
        for (ApAssociateWords apAssociateWord : apAssociateWords) {
            t.insert(apAssociateWord.getAssociateWords());
        }

        List<String> ret = t.startWith(dto.getSearchWords());
        List<ApAssociateWords> resultList  = new ArrayList<>();
        for (String s : ret) {
            ApAssociateWords aaw = new ApAssociateWords();
            aaw.setAssociateWords(s);
            resultList.add(aaw);
        }

        return ResponseResult.okResult(resultList);
    }

}
