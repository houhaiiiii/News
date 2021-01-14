package com.light.behavior.controller.v1;

import com.light.apis.behavior.ApUnlikesBehaviorControllerApi;
import com.light.behavior.service.ApUnlikesBehaviorService;
import com.light.model.behavior.dtos.UnLikesBehaviorDto;
import com.light.model.behavior.pojos.ApUnlikesBehavior;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/un_likes_behavior")
public class ApUnlikesBehaviorController implements ApUnlikesBehaviorControllerApi {

    @Autowired
    private ApUnlikesBehaviorService apUnlikesBehaviorService;

    @GetMapping("/one")
    @Override
    public ApUnlikesBehavior findUnLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId) {
        return apUnlikesBehaviorService.findUnLikeByArticleIdAndEntryId(entryId,articleId);
    }

    @PostMapping
    @Override
    public ResponseResult unlike(@RequestBody UnLikesBehaviorDto dto) {
        return apUnlikesBehaviorService.unlike(dto);
    }


}
