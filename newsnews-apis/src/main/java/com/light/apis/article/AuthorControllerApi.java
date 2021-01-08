package com.light.apis.article;

import com.light.model.article.pojos.ApAuthor;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * APP文章作者控制层接口
 * @author houhai
 */
public interface AuthorControllerApi {

    /**
     *根据用户id查询作者信息
     * @param id
     * @return
     */
    public ResponseResult findByUserId(@PathVariable("id") Integer id);

    /**
     * 保存作者
     * @param apAuthor
     * @return
     */
    public ResponseResult save(@RequestBody ApAuthor apAuthor);

    /**
     * 根据名称查询作者
     * @param name
     * @return
     */
    public ApAuthor findByName(@PathVariable("id") String name);

}