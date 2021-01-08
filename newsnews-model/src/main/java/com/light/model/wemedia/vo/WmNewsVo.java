package com.light.model.wemedia.vo;

import com.light.model.wemedia.pojos.WmNews;
import lombok.Data;

/**
 * vo:value object 值对象 / view object 表现层对象，
 * 主要对应页面显示（web页面）的数据对象。
 * @author houhai
 */
@Data
public class WmNewsVo  extends WmNews {
    /**
     * 作者名称
     */
    private String authorName;
}