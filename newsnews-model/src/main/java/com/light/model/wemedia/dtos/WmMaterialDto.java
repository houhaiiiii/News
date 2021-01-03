package com.light.model.wemedia.dtos;

import com.light.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class WmMaterialDto extends PageRequestDto {

    //1 查询收藏的
    Short isCollection;

}