package com.light.wemedia.controller.v1;

import com.light.apis.wemedia.WmNewsControllerApi;
import com.light.model.admin.dtos.NewsAuthDto;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.wemedia.dtos.WmNewsDto;
import com.light.model.wemedia.dtos.WmNewsPageReqDto;
import com.light.model.wemedia.pojos.WmNews;
import com.light.model.wemedia.vo.WmNewsVo;
import com.light.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 自媒体文章控制层
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController implements WmNewsControllerApi {

    @Autowired
    private WmNewsService wmNewsService;

    /**
     * 分页带条件查询自媒体文章列表
     * @param wmNewsPageReqDto
     * @return
     */
    @PostMapping("/list")
    @Override
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto wmNewsPageReqDto){
        return wmNewsService.findAll(wmNewsPageReqDto);
    }

    /**
     * 提交文章
     * @param wmNews
     * @return
     */
    @PostMapping("/submit")
    @Override
    public ResponseResult submitNews(@RequestBody WmNewsDto wmNews) {
        if(wmNews.getStatus()== WmNews.Status.SUBMIT.getCode()){
            //提交文章
            return wmNewsService.saveNews(wmNews, WmNews.Status.SUBMIT.getCode());
        }else{
            //保存草稿
            return wmNewsService.saveNews(wmNews, WmNews.Status.NORMAL.getCode());
        }
    }

    /**
     * 根据文章id查询文章
     * @param id
     * @return
     */
    @GetMapping("/one/{id}")
    @Override
    public ResponseResult findWmNewsById(@PathVariable("id") Integer id) {
        return wmNewsService.findWmNewsById(id);
    }

    /**
     * 根据ID删除文章
     * @param id
     * @return
     */
    @GetMapping("/del_news/{id}")
    @Override
    public ResponseResult delNews(Integer id) {
        return wmNewsService.delNews(id);
    }

    /**
     * 上下架文章
     * @param dto
     * @return
     */
    @RequestMapping("/down_or_up")
    @Override
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto) {
        return wmNewsService.downOrUp(dto);
    }

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    @GetMapping("/findOne/{id}")
    @Override
    public WmNews findById(Integer id) {
        return wmNewsService.getById(id);
    }

    /**
     * 修改文章
     * @param wmNews
     * @return
     */
    @PostMapping("/update")
    @Override
    public ResponseResult updateWmNews(WmNews wmNews) {
        wmNewsService.updateById(wmNews);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 查询需要发布的文章列表
     * @return
     */
    @GetMapping("/findRelease")
    @Override
    public List<Integer> findRelease() {
        return wmNewsService.findRelease();
    }

    /**
     * 分页查询文章信息
     * @param dto
     * @return
     */
    @PostMapping("/findList")
    @Override
    public PageResponseResult findList(@RequestBody NewsAuthDto dto) {
        return wmNewsService.findListAndPage(dto);
    }

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    @GetMapping("/find_news_vo/{id}")
    @Override
    public WmNewsVo findWmNewsVo(@PathVariable Integer id) {
        return wmNewsService.findWmNewsVo(id);
    }

}
