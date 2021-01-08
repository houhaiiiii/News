package com.light.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.article.mapper.ApArticleContentMapper;
import com.light.article.service.ApArticleContentService;
import com.light.model.article.pojos.ApArticleContent;
import org.springframework.stereotype.Service;

@Service
public class ApArticleContentServiceImpl extends ServiceImpl<ApArticleContentMapper,ApArticleContent> implements ApArticleContentService {

}
