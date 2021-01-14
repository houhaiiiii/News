package com.light.apis.search;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.search.dtos.UserSearchDto;

public interface ApAssociateWordsControllerApi {

    /**
     联想词
     @param userSearchDto
     @return
     */
    ResponseResult search(UserSearchDto userSearchDto);
}
