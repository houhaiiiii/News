package com.light.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.common.constans.message.NewsAutoScanConstants;
import com.light.common.constans.message.WmNewsMessageConstants;
import com.light.common.wemedia.WemediaContans;
import com.light.model.admin.dtos.NewsAuthDto;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.wemedia.dtos.WmNewsDto;
import com.light.model.wemedia.dtos.WmNewsPageReqDto;
import com.light.model.wemedia.pojos.WmMaterial;
import com.light.model.wemedia.pojos.WmNews;
import com.light.model.wemedia.pojos.WmNewsMaterial;
import com.light.model.wemedia.pojos.WmUser;
import com.light.model.wemedia.vo.WmNewsVo;
import com.light.utils.threadlocal.WmThreadLocalUtils;
import com.light.wemedia.mapper.WmMaterialMapper;
import com.light.wemedia.mapper.WmNewsMapper;
import com.light.wemedia.mapper.WmNewsMaterialMapper;
import com.light.wemedia.mapper.WmUserMapper;
import com.light.wemedia.service.WmNewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Value("${fdfs.url}")
    private String fileServerUrl;

    @Autowired
    WmMaterialMapper wmMaterialMapper;

    /**
     * 分页带条件查询自媒体文章列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAll(WmNewsPageReqDto dto) {

        //1.参数检查
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //分页参数检查
        dto.checkParam();

        //2.分页条件查询
        IPage pageParam = new Page(dto.getPage(), dto.getSize());

        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper();

        //状态精确查询
        if (dto.getStatus() != null) {
            lambdaQueryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }

        //频道精确查询
        if (null != dto.getChannelId()) {
            lambdaQueryWrapper.eq(WmNews::getChannelId, dto.getChannelId());
        }

        //时间范围查询
        if (dto.getBeginPubdate() != null && dto.getEndPubdate() != null) {
            lambdaQueryWrapper.between(WmNews::getPublishTime, dto.getBeginPubdate(), dto.getEndPubdate());
        }

        //关键字模糊查询
        if (null != dto.getKeyWord()) {
            lambdaQueryWrapper.like(WmNews::getTitle, dto.getKeyWord());
        }

        //查询当前登录用户的信息
        WmUser user = WmThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        lambdaQueryWrapper.eq(WmNews::getUserId, user.getId());

        //按照创建日期倒序
        lambdaQueryWrapper.orderByDesc(WmNews::getCreatedTime);

        //todo bug
        IPage pageResult = page(pageParam, lambdaQueryWrapper);

        //3.结果封装返回
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) pageResult.getTotal());
        responseResult.setData(pageResult.getRecords());
        responseResult.setHost(fileServerUrl);

        //2147483647
        //1302545498296557570

        return responseResult;
    }

    /**
     * 自媒体文章发布
     *
     * @param dto
     * @param isSubmit 是否为提交 1.提交 0.草稿
     * @return
     */
    @Override
    public ResponseResult saveNews(WmNewsDto dto, Short isSubmit) {

        //1.检查参数
        if (dto == null || StringUtils.isBlank(dto.getContent())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.保存或修改文章
        WmNews wmNews = new WmNews();
        BeanUtils.copyProperties(dto, wmNews);
        if (WemediaContans.WM_NEWS_TYPE_AUTO.equals(dto.getType())) {
            wmNews.setType(null);
        }
        if (dto.getImages() != null && dto.getImages().size() > 0) {
            //[dfjksdjfdfj.jpg,sdlkjfskld.jpg]
            wmNews.setImages(dto.getImages().toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(fileServerUrl, "")
                    .replace(" ", "")
            );
        }
        //保存或修改文章
        saveWmNews(wmNews, isSubmit);

        //3.关联文章与素材的关系
        String content = dto.getContent();
        List<Map> list = JSON.parseArray(content, Map.class);
        List<String> materials = ectractUrlInfo(list);

        //3.1 关联内容中的图片与素材的关系
        if (isSubmit == WmNews.Status.SUBMIT.getCode() && materials.size() != 0) {
            ResponseResult responseResult = saveRelativeInfoForContent(materials, wmNews.getId());
            if (responseResult != null) {
                return responseResult;
            }
        }

        //3.2 关联封面中图片与素材的关系,设置wm_news的type，自动
        if (isSubmit == WmNews.Status.SUBMIT.getCode()) {
            ResponseResult responseResult = saveRelativeInfoForCover(dto, materials, wmNews);
            if (responseResult != null) {
                return responseResult;
            }
        }

        return null;
    }

    /**
     * 设置封面图片与素材的关系
     *
     * @param dto
     * @param materials
     * @param wmNews
     * @return
     */
    private ResponseResult saveRelativeInfoForCover(WmNewsDto dto, List<String> materials, WmNews wmNews) {
        List<String> images = dto.getImages();
        //自动匹配封面
        if (dto.getType().equals(WemediaContans.WM_NEWS_TYPE_AUTO)) {
            //内容中的图片数量小于等于2 设置为单图
            if (materials.size() > 0 && materials.size() <= 2) {
                wmNews.setType(WemediaContans.WM_NEWS_SINGLE_IMAGE);
                images = materials.stream().limit(1).collect(Collectors.toList());
            } else if (materials.size() > 2) {
                //如果内容中的图片大于2 则设置为多图
                wmNews.setType(WemediaContans.WM_NEWS_MANY_IMAGE);
                images = materials.stream().limit(3).collect(Collectors.toList());
            } else {
                //如果内容中没有图片，则是无图
                wmNews.setType(WemediaContans.WM_NEWS_NONE_IMAGE);
            }
            //修改文章信息
            if (images != null && images.size() > 0) {
                wmNews.setImages(images.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(fileServerUrl, "")
                        .replace(" ", ""));
            }
            //更新
            updateById(wmNews);
        }
        //保存封面图片与素材的关系
        if (images != null && images.size() > 0) {
            ResponseResult responseResult = saveRelativeInfoForImage(images, wmNews.getId());
            if (responseResult != null) {
                return responseResult;
            }
        }
        return null;
    }

    /**
     * @param images
     * @param newsId
     * @return
     */
    private ResponseResult saveRelativeInfoForImage(List<String> images, Integer newsId) {
        List<String> materials = new ArrayList<>();
        for (String image : images) {
            materials.add(image.replace(fileServerUrl, ""));
        }
        return saveRelativeInfo(materials, newsId, WemediaContans.WM_NEWS_CONTENT_REFERENCE);
    }

    /**
     * 保存素材与文章内容的关系
     *
     * @param materials
     * @param newsId
     * @return
     */
    private ResponseResult saveRelativeInfoForContent(List<String> materials, Integer newsId) {
        return saveRelativeInfo(materials, newsId, WemediaContans.WM_NEWS_CONTENT_REFERENCE);
    }

    /**
     * 保存关系
     *
     * @param urls
     * @param newsId
     * @param type
     * @return
     */
    private ResponseResult saveRelativeInfo(List<String> urls, Integer newsId, Short type) {

        //去重
        urls = urls.stream().distinct().collect(Collectors.toList());

        //先根据URL查询素材信息
        Integer userId = WmThreadLocalUtils.getUser().getId();
        List<WmMaterial> wmMaterials = wmMaterialMapper.selectList(Wrappers.<WmMaterial>lambdaQuery().in(WmMaterial::getUrl, urls).eq(WmMaterial::getUserId, userId));

        //校验返回的数据
        if (urls.size() != wmMaterials.size()) {
            throw new RuntimeException("文章素材数据不匹配");
        }

        //获得素材的id集合
        List<Integer> materialIds = wmMaterials.stream().map(
                material -> material.getId()
        ).collect(Collectors.toList());

        //3.保存文章和素材的关联关系
        wmNewsMaterialMapper.saveRelationsByContent(materialIds, newsId, type);

        return null;

    }

    /**
     * 提取图片信息
     *
     * @param list
     * @return
     */
    private List<String> ectractUrlInfo(List<Map> list) {
        List<String> materials = new ArrayList<>();
        for (Map map : list) {
            if (map.get("type").equals(WemediaContans.WM_NEWS_TYPE_IMAGE)) {
                String imaUrl = (String) map.get("value");
                imaUrl = imaUrl.replace(fileServerUrl, "");
                materials.add(imaUrl);
            }
        }
        return materials;
    }

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 保存或修改文章
     *
     * @param wmNews
     * @param isSubmit
     */
    private void saveWmNews(WmNews wmNews, Short isSubmit) {
        wmNews.setStatus(isSubmit);
        wmNews.setUserId(WmThreadLocalUtils.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setEnable((short) 1);

        boolean flag = false;

        if (wmNews.getId() == null) {
            flag = save(wmNews);
        } else {
            //如果是修改，则先删除素材与文章的关系
            LambdaQueryWrapper<WmNewsMaterial> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(WmNewsMaterial::getNewsId, wmNews.getId());
            wmNewsMaterialMapper.delete(queryWrapper);
            flag = updateById(wmNews);
        }

        //发送消息
        if(flag){
            kafkaTemplate.send(NewsAutoScanConstants.WM_NEWS_AUTO_SCAN_TOPIC,JSON.toJSONString(wmNews.getId()));
        }

    }

    /**
     * 根据文章id查询文章
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult findWmNewsById(Integer id) {
        //1.参数检查
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "文章Id不可缺少");
        }
        //2.查询数据
        WmNews wmNews = getById(id);
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "文章不存在");
        }

        //3.结果返回
        ResponseResult responseResult = ResponseResult.okResult(wmNews);
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    /**
     * 根据ID删除文章
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult delNews(Integer id) {
        //1.检查参数
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "文章Id不可缺少");
        }

        //2.获取数据
        WmNews wmNews = getById(id);
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "文章不存在");
        }

        //3.判断当前文章状态 status==9  enable == 1
        if (wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode()) && wmNews.getEnable().equals(WemediaContans.WM_NEWS_ENABLE_UP)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "文章已发布，不能删除");
        }

        //4.去除素材与文章的关系
        wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId, wmNews.getId()));

        //5.删除文章
        removeById(wmNews.getId());
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 上下架文章
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult downOrUp(WmNewsDto dto) {
        //1.检查参数
        if (dto == null || dto.getId() == null) {
            ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询文章
        WmNews wmNews = getById(dto.getId());
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "文章不存在");
        }

        //3.判断文章是否发布
        if(!wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode())){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"当前文章不是发布状态，不能上下架");
        }

        //4.修改文章状态，同步到app端（后期做）TODO
        if(dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2){

            if(wmNews.getArticleId()!=null){
                Map<String,Object> mesMap = new HashMap<>();
                mesMap.put("enable",dto.getEnable());
                mesMap.put("articleId",wmNews.getArticleId());
                kafkaTemplate.send(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC,JSON.toJSONString(mesMap));
            }

            update(Wrappers.<WmNews>lambdaUpdate().eq(WmNews::getId,dto.getId()).set(WmNews::getEnable,dto.getEnable()));
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 查询需要发布的文章id列表
     * @return
     */
    @Override
    public List<Integer> findRelease(){
        List<WmNews> list = list(Wrappers.<WmNews>lambdaQuery().eq(WmNews::getStatus, 8).lt(WmNews::getPublishTime,new Date()));
        List<Integer> resultList = list.stream().map(WmNews::getId).collect(Collectors.toList());
        return resultList;
    }

    @Autowired
    private WmNewsMapper wmNewsMapper;

    /**
     * 分页查询文章信息
     * @param dto
     * @return
     */
    @Override
    public PageResponseResult findListAndPage(NewsAuthDto dto) {
        //1.检查参数
        dto.checkParam();
        //设置起始页
        dto.setPage((dto.getPage()-1)*dto.getSize());
        dto.setTitle("%"+dto.getTitle()+"%");

        //2.分页查询
        List<WmNewsVo> list = wmNewsMapper.findListAndPage(dto);
        //统计有多少条数据
        int count = wmNewsMapper.findListCount(dto);

        //3.结果返回
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), count);
        responseResult.setData(list);

        return responseResult;
    }

    @Autowired
    private WmUserMapper wmUserMapper;

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    @Override
    public WmNewsVo findWmNewsVo(Integer id) {
        //1.查询文章信息
        WmNews wmNews = getById(id);

        //2.查询作者
        WmUser wmUser = null;
        if (wmUser != null && wmNews.getUserId() != null) {
            wmUser = wmUserMapper.selectById(wmNews.getId());
        }

        //3.封装信息vo返回
        WmNewsVo wmNewsVo = new WmNewsVo();
        BeanUtils.copyProperties(wmNews,wmNewsVo);
        if (wmUser != null) {
            wmNewsVo.setAuthorName(wmUser.getName());
        }

        return wmNewsVo;
    }

}
