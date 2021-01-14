package com.light.article.service;

import com.light.model.article.dtos.ArticleInfoDto;
import com.light.model.common.dtos.ResponseResult;

/**
 * 文章详情展示业务层接口
 * @author houhai
 */
public interface ArticleInfoService {

    /**
     * 加载文章详情
     * @param dto
     * @return
     */
    public ResponseResult loadArticleInfo(ArticleInfoDto dto);

    /**
     * 加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢等
     * @param dto
     * @return
     */
    ResponseResult loadArticleBehavior(ArticleInfoDto dto);

}
