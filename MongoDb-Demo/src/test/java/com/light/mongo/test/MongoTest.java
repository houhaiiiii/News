package com.light.mongo.test;

import com.light.mongo.pojo.ApComment;
import com.light.mongo.repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void creat(){
        //创建文档集合,给的类需要有@Document才会自动映射
        mongoTemplate.createCollection(ApComment.class);
        //删除文档集合
        mongoTemplate.dropCollection(ApComment.class);
    }

    /**
     * 添加
     */
    @Test
    public void testCreat(){

        List<ApComment> list = new ArrayList<>();

        ApComment apComment1 = new ApComment();
        apComment1.setContent("1这是一个评论，21");
        apComment1.setLikes(21);

        ApComment apComment2 = new ApComment();
        apComment2.setContent("2这是一个评论，22");
        apComment2.setLikes(22);

        ApComment apComment3 = new ApComment();
        apComment3.setContent("3这是一个评论，23");
        apComment3.setLikes(23);

        list.add(apComment1);
        list.add(apComment2);
        list.add(apComment3);

        //插入数据
        Collection<ApComment> collection = mongoTemplate.insertAll(list);
        List<ApComment> list1 = collection.stream().collect(Collectors.toList());
        list1.forEach(apComment -> System.out.println(apComment));
    }

    /**
     * 查询一个
     */
    @Test
    public void testFindOne(){
        final String id = "";
        ApComment comment = mongoTemplate.findById(id, ApComment.class);
        System.out.println(comment);
    }

    /**
     * 查询所有
     */
    @Test
    public void testQuery(){
        List<ApComment> list = mongoTemplate.findAll(ApComment.class);
        for (ApComment apComment : list) {
            System.out.println(apComment);
        }
    }

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void findByContentLikeTest(){
        List<ApComment> commentList = commentRepository.findByContentLike("测试");
        commentList.forEach(comment -> System.out.println(comment));
    }

}
