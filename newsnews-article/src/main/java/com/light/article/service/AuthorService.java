package com.light.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.article.pojos.ApAuthor;

/**
 * App文章作者模块服务接口
 * @author houhai
 */
public interface AuthorService extends IService<ApAuthor> {

    /**
     * 根据用户id查询作者信息
     * @param userId
     * @return
     */
    public ResponseResult findByUserId(Integer userId);

    /**
     * 保存作者
     *
     * @param apAuthor
     * @return
     */
    public ResponseResult insert(ApAuthor apAuthor);
}
