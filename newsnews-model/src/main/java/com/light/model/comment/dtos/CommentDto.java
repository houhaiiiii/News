package com.light.model.comment.dtos;

import com.light.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class CommentDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 显示条数
     */
    private Integer size;

    // 最小点赞数
    private Long minLikes;

}
