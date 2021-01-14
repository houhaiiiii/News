package com.light.article.controller.v1;

import com.light.apis.article.ApArticleCollectionControllerApi;
import com.light.article.service.ApCollectionService;
import com.light.model.article.dtos.CollectionBehaviorDto;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收藏文章控制器
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/collection_behavior")
public class ApArticleCollectionController implements ApArticleCollectionControllerApi {

    @Autowired
    private ApCollectionService apCollectionService;

    @PostMapping
    @Override
    public ResponseResult collect(@RequestBody CollectionBehaviorDto dto) {
        return apCollectionService.collect(dto);
    }
}
