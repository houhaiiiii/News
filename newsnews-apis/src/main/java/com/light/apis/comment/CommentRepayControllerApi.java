package com.light.apis.comment;

import com.light.model.comment.dtos.CommentRepayDto;
import com.light.model.comment.dtos.CommentRepayLikeDto;
import com.light.model.comment.dtos.CommentRepaySaveDto;
import com.light.model.common.dtos.ResponseResult;

public interface CommentRepayControllerApi {

    /**
     * 加载评论回复列表
     * @param dto
     * @return
     */
    public ResponseResult loadCommentRepay(CommentRepayDto dto);

    /**
     * 保存回复内容
     * @param dto
     * @return
     */
    public ResponseResult saveCommentRepay(CommentRepaySaveDto dto);

    /**
     * 点赞回复内容
     * @param dto
     * @return
     */
    public ResponseResult saveCommentRepayLike(CommentRepayLikeDto dto);
}
