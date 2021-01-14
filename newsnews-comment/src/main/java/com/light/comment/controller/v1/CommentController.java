package com.light.comment.controller.v1;

import com.light.apis.comment.CommentControllerApi;
import com.light.comment.service.CommentService;
import com.light.model.comment.dtos.CommentDto;
import com.light.model.comment.dtos.CommentLikeDto;
import com.light.model.comment.dtos.CommentSaveDto;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论控制器
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController implements CommentControllerApi {

    @Autowired
    private CommentService commentService;

    @PostMapping("/load")
    @Override
    public ResponseResult findByArticleId(@RequestBody CommentDto dto){
        return commentService.findByArticleId(dto);
    }

    @PostMapping("/save")
    @Override
    public ResponseResult saveComment(@RequestBody CommentSaveDto dto){
        return commentService.saveComment(dto);
    }
    @PostMapping("/like")
    @Override
    public ResponseResult like(@RequestBody CommentLikeDto dto){
        return commentService.like(dto);
    }

}
