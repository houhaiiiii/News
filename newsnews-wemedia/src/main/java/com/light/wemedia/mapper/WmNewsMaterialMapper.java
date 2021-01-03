package com.light.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.model.wemedia.pojos.WmNewsMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图片删除数据层接口
 */
@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {

    /**
     *
     * @param materials
     * @param newId
     * @param type
     */
    public void saveRelationsByContent(@Param("materials") List<Integer> materials, @Param("newsId") Integer newId, @Param("type") int type);


}
