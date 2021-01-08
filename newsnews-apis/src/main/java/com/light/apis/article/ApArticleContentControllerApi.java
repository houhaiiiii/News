package com.light.apis.article;

import com.light.model.article.pojos.ApArticleContent;
import com.light.model.common.dtos.ResponseResult;

public interface ApArticleContentControllerApi {

    /**
     * 保存app端文章内容
     * @param apArticleContent
     * @return
     */
    ResponseResult saveArticleContent(ApArticleContent apArticleContent);
}