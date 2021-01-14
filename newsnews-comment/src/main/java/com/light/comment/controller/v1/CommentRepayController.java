package com.light.comment.controller.v1;

import com.light.apis.comment.CommentRepayControllerApi;
import com.light.comment.service.CommentRepayService;
import com.light.model.comment.dtos.CommentRepayDto;
import com.light.model.comment.dtos.CommentRepayLikeDto;
import com.light.model.comment.dtos.CommentRepaySaveDto;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment_repay")
public class CommentRepayController  implements CommentRepayControllerApi {

    @Autowired
    private CommentRepayService commentRepayService;

    @PostMapping("/load")
    @Override
    public ResponseResult loadCommentRepay(@RequestBody CommentRepayDto dto){
        return commentRepayService.loadCommentRepay(dto);
    }

    @PostMapping("/save")
    @Override
    public ResponseResult saveCommentRepay(@RequestBody CommentRepaySaveDto dto){
        return commentRepayService.saveCommentRepay(dto);
    }

    @PostMapping("/like")
    @Override
    public ResponseResult saveCommentRepayLike(@RequestBody CommentRepayLikeDto dto){
        return commentRepayService.saveCommentRepayLike(dto);
    }

}
