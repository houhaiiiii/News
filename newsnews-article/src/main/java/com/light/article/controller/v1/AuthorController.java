package com.light.article.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.light.apis.article.AuthorControllerApi;
import com.light.article.service.AuthorService;
import com.light.model.article.pojos.ApAuthor;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * App文章作者控制层
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/author")
public class AuthorController implements AuthorControllerApi {

    @Autowired
    private AuthorService authorService;

    /**
     * 根据id查询作者
     * @param id
     * @return
     */
    @GetMapping("/findByUserId/{id}")
    @Override
    public ResponseResult findByUserId(@PathVariable("id") Integer id) {
        return authorService.findByUserId(id);
    }

    /**
     * 新增作者
     * @param apAuthor
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody ApAuthor apAuthor) {
        return authorService.insert(apAuthor);
    }

    /**
     * 根据名称查询作者
     * @param name
     * @return
     */
    @GetMapping("/findByName/{name}")
    @Override
    public ApAuthor findByName(@PathVariable("name") String name) {
        ApAuthor apAuthor = authorService.getOne(Wrappers.<ApAuthor>lambdaQuery().eq(ApAuthor::getName, name));
        return apAuthor;
    }

}
