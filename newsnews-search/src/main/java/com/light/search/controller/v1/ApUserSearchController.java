package com.light.search.controller.v1;

import com.light.apis.search.ApUserSearchControllerApi;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.search.dtos.UserSearchDto;
import com.light.search.service.ApUserSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * APP用户搜索信息表 前端控制器
 * </p>
 *
 * @author itheima
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/history")
public class ApUserSearchController implements ApUserSearchControllerApi {

    @Autowired
    private ApUserSearchService apUserSearchService;

    @PostMapping("/load")
    @Override
    public ResponseResult findUserSearch(@RequestBody UserSearchDto userSearchDto) {
        return apUserSearchService.findUserSearch(userSearchDto);
    }

    @PostMapping("/del")
    @Override
    public ResponseResult delUserSearch(@RequestBody UserSearchDto userSearchDto) {
        return apUserSearchService.delUserSearch(userSearchDto);
    }

}
