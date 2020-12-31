package com.light.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.user.pojos.ApUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户审核数据访问层
 * @author houhai
 */
@Mapper
public interface ApUserMapper extends BaseMapper<ApUser> {

}
