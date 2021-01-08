package com.light.apis.article;

import com.light.model.article.pojos.ApArticleConfig;
import com.light.model.common.dtos.ResponseResult;

public interface ApArticleConfigControllerApi {

    /**
     * 保存app端文章配置
     * @param apArticleConfig
     * @return
     */
    public ResponseResult saveArticleConfig(ApArticleConfig apArticleConfig);
}