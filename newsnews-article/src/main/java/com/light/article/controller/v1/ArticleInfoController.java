package com.light.article.controller.v1;

import com.light.apis.article.ArticleInfoControllerApi;
import com.light.article.service.ArticleInfoService;
import com.light.model.article.dtos.ArticleInfoDto;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章列表详情控制实现类
 *
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleInfoController implements ArticleInfoControllerApi {

    @Autowired
    ArticleInfoService articleInfoService;

    /**
     * 加载文章详情
     *
     * @param dto
     * @return
     */
    @PostMapping("/load_article_info")
    @Override
    public ResponseResult loadArticleInfo(@RequestBody ArticleInfoDto dto) {
        return articleInfoService.loadArticleInfo(dto);
    }

    /**
     * 加载文章详情的行为内容
     *
     * @param dto
     * @return
     */
    @PostMapping("/load_article_behavior")
    @Override
    public ResponseResult loadArticleBehavior(@RequestBody ArticleInfoDto dto) {
        return articleInfoService.loadArticleBehavior(dto);
    }

}
