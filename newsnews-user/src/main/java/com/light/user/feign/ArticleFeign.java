package com.light.user.feign;

import com.light.model.common.dtos.ResponseResult;
import com.light.user.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.light.model.article.pojos.ApAuthor;

/**
 * 自媒体feign远程服务调用接口
 * @author houhai
 */
@FeignClient(value = "newsnews-article",configuration = FeignConfig.class)
public interface ArticleFeign {

    //           /api/v1/author/findByUserId/{id}
    @GetMapping("/api/v1/author/findByUserId/{id}")
    public ApAuthor selectById(@PathVariable("id") Integer id);

    @PostMapping("/api/v1/author/save")
    public ResponseResult save(@RequestBody ApAuthor apAuthor);

}
