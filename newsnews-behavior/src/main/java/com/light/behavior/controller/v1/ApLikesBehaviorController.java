package com.light.behavior.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.light.apis.behavior.ApLikesBehaviorControllerApi;
import com.light.behavior.service.ApLikesBehaviorService;
import com.light.model.behavior.dtos.LikesBehaviorDto;
import com.light.model.behavior.pojos.ApLikesBehavior;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes_behavior")
public class ApLikesBehaviorController implements ApLikesBehaviorControllerApi {

    @Autowired
    private ApLikesBehaviorService apLikesBehaviorService;

    /**
     * 记录点赞行为
     * @param dto
     * @return
     */
    @PostMapping
    @Override
    public ResponseResult like(@RequestBody LikesBehaviorDto dto) {
        return apLikesBehaviorService.like(dto);
    }

    /**
     * 根据行为实体id和文章id查询点赞行为
     * @param articleId
     * @param entryId
     * @param type
     * @return
     */
    @PostMapping("/one")
    @Override
    public ApLikesBehavior findLikeByArticleIdAndEntryId(@RequestParam("articleId") Long articleId, @RequestParam("entryId")Integer entryId, @RequestParam("type")Short type) {

        return apLikesBehaviorService.findLikeByArticleIdAndEntryId(articleId,entryId,type);


    }

}
