package com.light.model.behavior.dtos;

import com.light.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class LikesBehaviorDto {

    // 设备ID
    @IdEncrypt
    Integer equipmentId;

    // 文章、动态、评论等ID
    @IdEncrypt
    Long articleId;

    /**
     * 喜欢内容类型
     * 0文章
     * 1动态
     * 2评论
     */
    Short type;

    /**
     * 喜欢操作方式
     * 0 点赞
     * 1 取消点赞
     */
    Short operation;
}
