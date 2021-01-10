package com.light.admin.feign;

import com.light.model.article.pojos.ApArticle;
import com.light.model.article.pojos.ApArticleConfig;
import com.light.model.article.pojos.ApArticleContent;
import com.light.model.article.pojos.ApAuthor;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * feign远程调用Article服务
 * @author houhai
 */
@FeignClient("newsnews-article")
public interface ArticleFeign {

    @PostMapping("/api/v1/article_config/save")
    ResponseResult saveArticleConfig(@RequestBody ApArticleConfig apArticleConfig);

    @GetMapping("/api/v1/author/findByName/{name}")
    ApAuthor selectAuthorByName(@PathVariable("name") String name);

    @PostMapping("/api/v1/article/save")
    ApArticle saveArticle(@RequestBody ApArticle apArticle);

    @PostMapping("/api/v1/article_content/save")
    ResponseResult saveArticleContent(@RequestBody ApArticleContent apArticleContent);

    @GetMapping("/api/v1/author/one/{id}")
    ApAuthor selectById(@PathVariable("id") Integer id);

}
