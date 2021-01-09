package com.light.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.light.article.mapper.ApArticleConfigMapper;
import com.light.article.mapper.ApArticleContentMapper;
import com.light.article.service.ArticleInfoService;
import com.light.model.article.dtos.ArticleInfoDto;
import com.light.model.article.pojos.ApArticleConfig;
import com.light.model.article.pojos.ApArticleContent;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 文章详情展示业务层实现类
 * @author houhai
 */
@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    /**
     * 加载文章详情
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadArticleInfo(ArticleInfoDto dto) {

        HashMap<String, Object> resultMap = new HashMap<>();

        //1.校验参数
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询文章的配置
        ApArticleConfig apArticleConfig = apArticleConfigMapper.selectOne(Wrappers.<ApArticleConfig>lambdaQuery().eq(ApArticleConfig::getArticleId,dto.getArticleId()));
        if (apArticleConfig == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //3.查询文章的内容
        if (!apArticleConfig.getIsDelete() && !apArticleConfig.getIsDown()) {
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, dto.getArticleId()));
            resultMap.put("content",apArticleContent);
        }
        resultMap.put("config",apArticleConfig);

        //4.结果返回
        return ResponseResult.okResult(resultMap);
    }

}
