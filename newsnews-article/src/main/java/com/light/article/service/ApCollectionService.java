package com.light.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.article.dtos.CollectionBehaviorDto;
import com.light.model.article.pojos.ApCollection;
import com.light.model.common.dtos.ResponseResult;

public interface ApCollectionService extends IService<ApCollection> {

    /**
     * 收藏文章
     * @param dto
     * @return
     */
    public ResponseResult collect(CollectionBehaviorDto dto);

}
