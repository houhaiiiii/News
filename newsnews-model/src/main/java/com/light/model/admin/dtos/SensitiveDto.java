package com.light.model.admin.dtos;

import lombok.Data;

/**
 *
 * @author houhai
 */
@Data
public class SensitiveDto {

    /**
     * 敏感词名称
     */
    private String name;

    private Integer page;

    private Integer size;

    public void checkParam(){

    }

}
