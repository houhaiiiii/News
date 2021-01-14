package com.light.apis.comment;

import com.light.model.comment.dtos.CommentSaveDto;
import com.light.model.common.dtos.ResponseResult;

public interface CommentControllerApi {

    /**
     * 保存评论
     * @param dto
     * @return
     */
    public ResponseResult saveComment(CommentSaveDto dto);
}
