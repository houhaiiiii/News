package com.light.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.article.dtos.ArticleHomeDto;
import com.light.model.article.pojos.ApArticle;
import com.light.model.common.dtos.ResponseResult;

/**
 * 文章信息业务层接口
 * @author houhai
 */
public interface ApArticleService extends IService<ApArticle> {

    /**
     * 根据参数加载文章列表
     * @param loadType 1为加载更多  2为加载最新
     * @param dto
     * @return
     */
    ResponseResult load(Short loadType, ArticleHomeDto dto);

}