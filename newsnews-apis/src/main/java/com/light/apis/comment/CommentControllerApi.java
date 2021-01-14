package com.light.apis.comment;

import com.light.model.comment.dtos.CommentDto;
import com.light.model.comment.dtos.CommentLikeDto;
import com.light.model.comment.dtos.CommentSaveDto;
import com.light.model.common.dtos.ResponseResult;

/**
 * APP端评论控制器接口
 * @author houhai
 */
public interface CommentControllerApi {

    /**
     * 保存评论
     * @param dto
     * @return
     */
    public ResponseResult saveComment(CommentSaveDto dto);

    /**
     * 点赞某一条评论
     * @param dto
     * @return
     */
    public ResponseResult like(CommentLikeDto dto);

    /**
     * 查询评论
     * @param dto
     * @return
     */
    public ResponseResult findByArticleId(CommentDto dto);

}
