package com.light.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.dtos.WmNewsDto;
import com.light.model.wemedia.dtos.WmNewsPageReqDto;
import com.light.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 分页带条件查询自媒体文章列表
     * @param wmNewsPageReqDto
     * @return
     */
    public ResponseResult findAll(WmNewsPageReqDto wmNewsPageReqDto);


    /**
     * 自媒体文章发布
     * @param dto
     * @param isSubmit 是否为提交 1.提交 0.草稿
     * @return
     */
    public ResponseResult saveNews(WmNewsDto dto, Short isSubmit);

    /**
     * 根据文章id查询文章
     * @param id
     * @return
     */
    ResponseResult findWmNewsById(Integer id);

    /**
     * 删除文章
     * @return
     */
    ResponseResult delNews(Integer id);

    /**
     * 上下架
     * @param dto
     * @return
     */
    ResponseResult downOrUp(WmNewsDto dto);

}
