package com.light.model.article.dtos;

import lombok.Data;

import java.util.Date;

/**
 * @author houhai
 */
@Data
public class ArticleHomeDto {

    // 最大时间
    Date maxBehotTime;
    // 最小时间
    Date minBehotTime;
    // 分页size
    Integer size;
    // 数据范围，比如频道ID
    String tag;

}
