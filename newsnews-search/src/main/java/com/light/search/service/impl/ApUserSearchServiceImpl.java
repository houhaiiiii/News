package com.light.search.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.model.behavior.pojos.ApBehaviorEntry;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.search.dtos.UserSearchDto;
import com.light.model.search.pojos.ApUserSearch;
import com.light.model.user.pojos.ApUser;
import com.light.search.feign.BehaviorFeign;
import com.light.search.mapper.ApUserSearchMapper;
import com.light.search.service.ApUserSearchService;
import com.light.utils.threadlocal.AppThreadLocalUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Log4j2
public class ApUserSearchServiceImpl extends ServiceImpl<ApUserSearchMapper, ApUserSearch> implements ApUserSearchService {

    /**
     * 查询搜索记录
     * @param userSearchDto
     * @return
     */
    @Override
    public ResponseResult findUserSearch(UserSearchDto userSearchDto) {
        //1.检查数据
        if (userSearchDto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询行为实体
        ApBehaviorEntry apBehaviorEntry = getEntry(userSearchDto);
        if (apBehaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //3.分页查询，默认查询5条数据返回
        Page pageParam = new Page(0, userSearchDto.getPageSize());
        IPage<ApUserSearch> page = page(pageParam, Wrappers.<ApUserSearch>lambdaQuery().eq(ApUserSearch::getEntryId, apBehaviorEntry.getId()).eq(ApUserSearch::getStatus, 1));

        return ResponseResult.okResult(page.getRecords());
    }

    @Autowired
    private BehaviorFeign behaviorFeign;

    /**
     * 获取行为实体
     * @param dto
     * @return
     */
    private ApBehaviorEntry getEntry(UserSearchDto dto){
        ApUser user = AppThreadLocalUtils.getUser();
        ApBehaviorEntry entry = behaviorFeign.findByUserIdOrEntryId(user.getId(), dto.getEquipmentId());
        return entry;
    }

    /**
     * 删除搜索记录
     * @param userSearchDto
     * @return
     */
    @Override
    public ResponseResult delUserSearch(UserSearchDto userSearchDto) {
        //1.检查参数
        if (userSearchDto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.更新当前搜索记录的状态 status 0
        ApBehaviorEntry apBehaviorEntry = getEntry(userSearchDto);
        if (apBehaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        update(Wrappers.<ApUserSearch>lambdaUpdate().eq(ApUserSearch::getId,userSearchDto.getEquipmentId()).eq(ApUserSearch::getEntryId,apBehaviorEntry.getId())
                .set(ApUserSearch::getStatus,0));
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 插入搜索记录
     * @param entryId
     * @param searchWords
     */
    @Override
    @Async("taskExecutor")
    public void insert(Integer entryId, String searchWords) {
        //1.查询当前搜索记录
        ApUserSearch apUserSearch = getOne(Wrappers.<ApUserSearch>lambdaQuery().eq(ApUserSearch::getEntryId, entryId).eq(ApUserSearch::getKeyword, searchWords));

        //2.如果存在 更新状态
        if (apUserSearch != null && apUserSearch.getStatus() == 1) {
            log.info("当前关键字已存在，无需再次保存");
            return;
        }else if(apUserSearch != null && apUserSearch.getStatus() == 0){
            apUserSearch.setStatus(1);
            updateById(apUserSearch);
            return;
        }

        //3.如果不存在，保存新的数据
        apUserSearch = new ApUserSearch();
        apUserSearch.setEntryId(entryId);
        apUserSearch.setStatus(1);
        apUserSearch.setKeyword(searchWords);
        apUserSearch.setCreatedTime(new Date());
        save(apUserSearch);

    }

}
