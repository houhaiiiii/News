package com.light.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.user.pojos.ApUserFollow;
import org.apache.ibatis.annotations.Mapper;

/**
 * APP用户关注信息表
 * @author houhai
 */
@Mapper
public interface ApUserFollowMapper extends BaseMapper<ApUserFollow> {
}
