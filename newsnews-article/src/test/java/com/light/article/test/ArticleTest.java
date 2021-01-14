package com.light.article.test;

import com.light.article.ArticleApplication;
import com.light.article.service.ApArticleService;
import com.light.model.article.pojos.ApArticle;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class ArticleTest {

    @Autowired
    private ApArticleService articleService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testImportAll() throws IOException {

        List<ApArticle> list = articleService.list();

        for (ApArticle apArticle : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", apArticle.getId());
            map.put("publishTime", apArticle.getPublishTime());
            map.put("layout", apArticle.getLayout());
            map.put("images", apArticle.getImages());
            map.put("authorId", apArticle.getAuthorId());
            map.put("title", apArticle.getTitle());
            IndexRequest indexRequest = new IndexRequest("app_info_article_wq").id(apArticle.getId().toString()).source(map);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        }

    }

}