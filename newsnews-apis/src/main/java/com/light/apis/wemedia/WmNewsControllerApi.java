package com.light.apis.wemedia;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.dtos.WmNewsDto;
import com.light.model.wemedia.dtos.WmNewsPageReqDto;
import com.light.model.wemedia.pojos.WmNews;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 自媒体文章接口
 * @author houhai
 */
public interface WmNewsControllerApi {

    /**
     * 分页带条件查询自媒体文章列表
     * @param wmNewsPageReqDto
     * @return
     */
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto wmNewsPageReqDto);

    /**
     * 提交文章
     * @param wmNews
     * @return
     */
    ResponseResult submitNews(@RequestBody WmNewsDto wmNews);

    /**
     * 根据id获取文章信息
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
    ResponseResult downOrUp(@RequestBody WmNewsDto dto);

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    WmNews findById(Integer id);

    /**
     * 修改文章
     * @param wmNews
     * @return
     */
    ResponseResult updateWmNews(WmNews wmNews);

}
