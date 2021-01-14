package com.light.mongo.repository;

import com.light.mongo.pojo.ApComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论仓库类
 * @author houhai
 */
@Repository
public interface CommentRepository extends MongoRepository<ApComment,String> {

    //根据评论内容模糊查询
    public List<ApComment> findByContentLike(String like);

}
