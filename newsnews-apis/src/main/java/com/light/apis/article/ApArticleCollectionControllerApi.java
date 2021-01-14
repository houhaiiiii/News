package com.light.apis.article;

import com.light.model.article.dtos.CollectionBehaviorDto;
import com.light.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "app端-文章收藏功能", tags = "article-collection", description = "app端-文章收藏功能")
public interface ApArticleCollectionControllerApi {

    @ApiOperation("收藏文章")
    public ResponseResult collect(CollectionBehaviorDto dto);
}
