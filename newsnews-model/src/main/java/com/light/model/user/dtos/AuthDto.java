package com.light.model.user.dtos;

import com.light.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 用户审核信息类
 * @author houhai
 */
@Data
public class AuthDto extends PageRequestDto {

    private Integer id;

    //驳回的信息
    private String msg;

    //状态
    private Short status;

}