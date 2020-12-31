package com.light.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.article.pojos.ApAuthor;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author houhai
 */
@Mapper
public interface AuthorMapper extends BaseMapper<ApAuthor> {
}