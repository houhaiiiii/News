package com.light.model.comment.dtos;

import lombok.Data;

/**
 * 点赞回复内容参数
 * @author houhai
 */
@Data
public class CommentRepayLikeDto {

    /**
     * 回复id
     */
    private String commentRepayId;

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}
