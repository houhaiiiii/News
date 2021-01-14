package com.light.search.controller.v1;

import com.light.apis.search.ArticleSearchControllerApi;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.search.dtos.UserSearchDto;
import com.light.search.service.ApArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章搜索控制器
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/article/search")
public class ArticleSearchController implements ArticleSearchControllerApi {

    @Autowired
    private ApArticleSearchService apArticleSearchService;

    @PostMapping("/search")
    @Override
    public ResponseResult search(@RequestBody UserSearchDto userSearchDto) {
        try {
            return apArticleSearchService.search(userSearchDto);
        } catch (Exception e) {
            e.printStackTrace();
            //返回错误信息
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
        }
    }
}
