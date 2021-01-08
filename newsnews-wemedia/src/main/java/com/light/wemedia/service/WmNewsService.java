package com.light.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.admin.dtos.NewsAuthDto;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.dtos.WmNewsDto;
import com.light.model.wemedia.dtos.WmNewsPageReqDto;
import com.light.model.wemedia.pojos.WmNews;
import com.light.model.wemedia.vo.WmNewsVo;

import java.util.List;

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

    /**
     * 查询需要发布的文章id列表
     * @return
     */
    List<Integer> findRelease();

    /**
     * 分页查询文章信息
     * @param dto
     * @return
     */
    public PageResponseResult findListAndPage(NewsAuthDto dto);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    WmNewsVo findWmNewsVo(Integer id);

}
