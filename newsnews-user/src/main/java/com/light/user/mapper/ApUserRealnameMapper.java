package com.light.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.user.pojos.ApUserRealname;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户认证数据访问层接口
 * @author houhai
 */
@Mapper
public interface ApUserRealnameMapper extends BaseMapper<ApUserRealname> {
}
