package com.light.apis.article;


import com.light.model.article.dtos.ArticleInfoDto;
import com.light.model.common.dtos.ResponseResult;

/**
 * 文章详情展示控制层父接口
 * @author houhai
 */
public interface ArticleInfoControllerApi {

    /**
     * 加载文章详情
     *
     * @param dto
     * @return
     */
    public ResponseResult loadArticleInfo(ArticleInfoDto dto);

}

