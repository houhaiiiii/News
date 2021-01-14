package com.light.model.comment.dtos;

import lombok.Data;

/**
 * 保存回复内容参数
 * @author houhai
 */
@Data
public class CommentRepaySaveDto {

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 回复内容
     */
    private String content;
}
