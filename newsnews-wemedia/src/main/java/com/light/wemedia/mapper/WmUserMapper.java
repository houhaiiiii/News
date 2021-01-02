package com.light.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.wemedia.pojos.WmUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自媒体用户数据访问层
 * @author houhai
 */
@Mapper
public interface WmUserMapper extends BaseMapper<WmUser> {



}
