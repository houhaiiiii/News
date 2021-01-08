package com.light.article.controller.v1;

import com.light.apis.article.ApArticleControllerApi;
import com.light.article.service.ApArticleService;
import com.light.model.article.pojos.ApArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * App文章信息控制层实现类
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/article")
public class ApArticleController implements ApArticleControllerApi {

    @Autowired
    private ApArticleService apArticleService;

    @RequestMapping("save")
    @Override
    public ApArticle saveArticle(@RequestBody ApArticle apArticle) {
        apArticleService.save(apArticle);
        return apArticle;
    }

}
