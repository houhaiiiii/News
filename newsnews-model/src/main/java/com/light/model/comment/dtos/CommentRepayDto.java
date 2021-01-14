package com.light.model.comment.dtos;

import lombok.Data;

/**
 * 加载评论回复列表参数
 * @author houhai
 */
@Data
public class CommentRepayDto {

    /**
     * 评论id
     */
    private String commentId;

    private Integer size;

    // 最小时间
    private Long minLikes;
}
