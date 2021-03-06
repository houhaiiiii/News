package com.light.apis.wemedia;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.dtos.WmMaterialDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 自媒体素材控制层接口
 * @author houhai
 */
public interface WmMaterialControllerApi {

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
     * 取消收藏
     * @param id
     * @return
     */
    public ResponseResult cancelCollectionMaterial(Integer id);

    /**
     * 收藏
     * @param id
     * @return
     */
    public ResponseResult collectionMaterial(Integer id);

}
