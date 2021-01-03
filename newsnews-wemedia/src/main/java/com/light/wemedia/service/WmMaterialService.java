package com.light.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.dtos.WmMaterialDto;
import com.light.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * 自媒体图文素材信息业务层接口
 * @author houhai
 */
public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 素材-上传图片
     * @param multipartFile
     * @return
     */
    public ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 查询素材列表
     * @return
     */
    public ResponseResult findList(WmMaterialDto dto);

    /**
     * 根据id删除图片
     * @param id
     * @return
     */
    public ResponseResult delPicture(Integer id);

    /**
     * 收藏与取消收藏
     * @param id
     * @param type
     * @return
     */
    public ResponseResult updateStatus(Integer id,Short type);

}
