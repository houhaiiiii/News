package com.light.admin.feign;

import com.light.model.admin.dtos.NewsAuthDto;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.pojos.WmNews;
import com.light.model.wemedia.pojos.WmUser;
import com.light.model.wemedia.vo.WmNewsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient("newsnews-wemedia")
public interface WemediaFeign {

    @GetMapping("/api/v1/news/findOne/{id}")
    WmNews findById(@PathVariable("id") Integer id);

    @PostMapping("/api/v1/news/update")
    ResponseResult updateWmNews(WmNews wmNews);

    @GetMapping("/api/v1/user/findOne/{id}")
    WmUser findWmUserById(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/news/findRelease")
    List<Integer> findRelease();

    /**
     * 在查询列表的方法需要通过远程接口进行分页查看
     * @param dto
     * @return
     */
    @PostMapping("/api/v1/news/findList/")
    public PageResponseResult findList(NewsAuthDto dto);

    @GetMapping("/api/v1/news/find_news_vo/{id}")
    public WmNewsVo findWmNewsVo(@PathVariable("id") Integer id);

}
