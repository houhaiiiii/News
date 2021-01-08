package com.light.article.controller.v1;

import com.light.apis.article.ApArticleConfigControllerApi;
import com.light.article.service.ApArticleConfigService;
import com.light.model.article.pojos.ApArticleConfig;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article_config")
public class ApArticleConfigController implements ApArticleConfigControllerApi {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    /**
     * 保存文章配置
     * @param apArticleConfig
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult saveArticleConfig(@RequestBody ApArticleConfig apArticleConfig) {
        apArticleConfigService.save(apArticleConfig);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
