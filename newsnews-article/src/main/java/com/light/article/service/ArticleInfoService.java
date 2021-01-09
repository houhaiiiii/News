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

}
