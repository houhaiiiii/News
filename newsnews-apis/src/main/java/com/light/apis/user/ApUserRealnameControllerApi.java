package com.light.apis.user;

import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.AuthDto;

/**
 * 用户实名认证接口
 * @author houhai
 */
public interface ApUserRealnameControllerApi {

    /**
     *按照状态查询用户认证列表
     * @param dto
     * @return
     */
    public PageResponseResult loadListByStatus(AuthDto dto);

    /**
     * 审核通过
     * @param dto
     * @return
     */
    public ResponseResult authPass(AuthDto dto) ;

    /**
     * 审核失败
     * @param dto
     * @return
     */
    public ResponseResult authFail(AuthDto dto);

}
