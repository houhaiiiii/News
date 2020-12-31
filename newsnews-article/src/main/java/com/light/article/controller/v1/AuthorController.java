package com.light.article.controller.v1;

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

    @GetMapping("/findByUserId/{id}")
    @Override
    public ResponseResult findByUserId(@PathVariable("id") Integer id) {
        return authorService.findByUserId(id);
    }

    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody ApAuthor apAuthor) {
        return authorService.insert(apAuthor);
    }

}
