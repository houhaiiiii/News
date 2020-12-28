package com.light.admin.controller.v1;

import com.light.admin.service.AdSensitiveService;
import com.light.apis.admin.AdSensitiveControllerApi;
import com.light.model.admin.dtos.SensitiveDto;
import com.light.model.admin.pojos.AdSensitive;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 敏感词模块控制层
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/sensitive")
public class AdSensitiveController implements AdSensitiveControllerApi {

    @Autowired
    private AdSensitiveService adSensitiveService;

    /**
     * 根据名称分页查询敏感词
     * @param dto
     * @return
     */
    @PostMapping("/list")
    @Override
    public ResponseResult list(@RequestBody SensitiveDto dto) {
        return adSensitiveService.list(dto);
    }

    /**
     * 新增
     * @param dto
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody AdSensitive dto) {
        return adSensitiveService.insert(dto);
    }

    /**
     * 更新
     * @param dto
     * @return
     */
    @PostMapping("/update")
    @Override
    public ResponseResult update(@RequestBody AdSensitive dto) {
        return adSensitiveService.update(dto);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    @Override
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        return adSensitiveService.deleteById(id);
    }
}
