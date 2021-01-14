package com.light.comment.service;

import com.light.model.comment.dtos.CommentDto;
import com.light.model.comment.dtos.CommentLikeDto;
import com.light.model.comment.dtos.CommentSaveDto;
import com.light.model.common.dtos.ResponseResult;

public interface CommentService {

    /**
     * 保存评论
     * @return
     */
    public ResponseResult saveComment(CommentSaveDto dto);

    /**
     * 点赞评论
     * @param dto
     * @return
     */
    public ResponseResult like(CommentLikeDto dto);

    /**
     * 根据文章id查询评论列表
     * @return
     */
    public ResponseResult findByArticleId(CommentDto dto);

}
