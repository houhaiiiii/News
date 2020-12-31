package com.light.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.article.mapper.AuthorMapper;
import com.light.article.service.AuthorService;
import com.light.model.article.pojos.ApAuthor;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * App文章作者业务实现类
 * @author houhai
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, ApAuthor> implements AuthorService {

    @Override
    public ResponseResult findByUserId(Integer userId) {
        //参数校验
        if (userId == null) {
            return  ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //根据用户ID查询作者信息
        ApAuthor author = getOne(Wrappers.<ApAuthor>lambdaQuery().eq(ApAuthor::getUserId, userId));

        return ResponseResult.okResult(author);
    }

    @Override
    public ResponseResult insert(ApAuthor apAuthor) {

        //参数校验
        if (apAuthor == null) {
            return  ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //保存作者信息
        apAuthor.setCreatedTime(new Date());
        save(apAuthor);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }
}
