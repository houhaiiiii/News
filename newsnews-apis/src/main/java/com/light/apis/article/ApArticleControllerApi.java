package com.light.apis.article;

import com.light.model.article.pojos.ApArticle;

public interface ApArticleControllerApi {

    /**
     * 保存app文章
     * @param apArticle
     * @return
     */
    ApArticle saveArticle(ApArticle apArticle);
}
