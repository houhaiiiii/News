package com.light.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.admin.pojos.AdUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问层
 * @author houhai
 */
@Mapper
public interface AdUserMapper extends BaseMapper<AdUser> {
}
