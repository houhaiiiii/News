package com.light.apis.search;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.search.dtos.UserSearchDto;

public interface ArticleSearchControllerApi {

    /**
     *  搜索文章
     * @param userSearchDto
     * @return
     */
    public ResponseResult search(UserSearchDto userSearchDto);

}
