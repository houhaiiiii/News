package com.light.model.comment.dtos;

import lombok.Data;

/**
 * 评论点赞前端展示实体类
 * @author houhai
 */
@Data
public class CommentLikeDto {

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;

}
