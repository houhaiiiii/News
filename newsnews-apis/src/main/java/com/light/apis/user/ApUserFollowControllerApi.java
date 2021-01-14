package com.light.apis.user;

import com.light.model.user.pojos.ApUserFollow;

public interface ApUserFollowControllerApi {

    /**
     * 根据用户id和关注作者的id查询
     * @param userId
     * @param followId
     * @return
     */
    public ApUserFollow findByUserIdAndFollowId(Integer userId, Integer followId);

}
