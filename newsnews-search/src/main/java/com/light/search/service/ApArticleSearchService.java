package com.light.search.service;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.search.dtos.UserSearchDto;

import java.io.IOException;

/**
 * 文章搜索业务层接口
 * @author houhai
 */
public interface ApArticleSearchService {

    /**
     ES文章分页搜索
     @return
     */
    ResponseResult search(UserSearchDto userSearchDto) throws IOException;
}
