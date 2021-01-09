package com.light.model.article.dtos;

import com.light.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class ArticleInfoDto {

    // 文章ID
    @IdEncrypt
    Long articleId;

}
