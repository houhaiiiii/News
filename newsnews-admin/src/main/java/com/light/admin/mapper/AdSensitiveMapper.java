package com.light.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.admin.pojos.AdSensitive;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 敏感词数据访问层
 * @author houhai
 */
@Mapper
public interface AdSensitiveMapper extends BaseMapper<AdSensitive> {

    /**
     * 查询所有敏感词
     * @return
     */
    public List<String> findAllSensitive();

}
