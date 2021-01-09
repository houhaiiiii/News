package com.light.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.article.dtos.ArticleHomeDto;
import com.light.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章信息数据层接口
 * @author houhai
 */
@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    /**
     * 加载文章列表
     * @param dto
     * @param type
     * @return
     */
    public List<ApArticle> loadArticleList(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

}