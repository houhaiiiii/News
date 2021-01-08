package com.light.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章信息数据层接口
 * @author houhai
 */
@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {
}