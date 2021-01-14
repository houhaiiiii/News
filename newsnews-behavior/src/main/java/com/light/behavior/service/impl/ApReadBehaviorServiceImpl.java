package com.light.behavior.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.behavior.mapper.ApReadBehaviorMapper;
import com.light.behavior.service.ApBehaviorEntryService;
import com.light.behavior.service.ApReadBehaviorService;
import com.light.model.behavior.dtos.ReadBehaviorDto;
import com.light.model.behavior.pojos.ApBehaviorEntry;
import com.light.model.behavior.pojos.ApReadBehavior;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.user.pojos.ApUser;
import com.light.utils.threadlocal.AppThreadLocalUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 阅读行为的业务层实现类
 * @author houhai
 */
@Service
@Log4j2
public class ApReadBehaviorServiceImpl extends ServiceImpl<ApReadBehaviorMapper, ApReadBehavior> implements ApReadBehaviorService {

    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @Override
    public ResponseResult readBehavior(ReadBehaviorDto dto) {

        //1.参数校验
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询用户行为实体
        //从线程中获得用户ID
        ApUser user = AppThreadLocalUtils.getUser();
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryService.findByUserIdOrEquipmentId(user.getId(),dto.getEquipmentId());
        if(apBehaviorEntry == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //3.保存或更新阅读的行为
        ApReadBehavior apReadBehavior = getOne(Wrappers.<ApReadBehavior>lambdaQuery().eq(ApReadBehavior::getEntryId, apBehaviorEntry.getId()).eq(ApReadBehavior::getArticleId, dto.getArticleId()));
        if(apReadBehavior == null){
            apReadBehavior = new ApReadBehavior();
            apReadBehavior.setCount(dto.getCount());
            apReadBehavior.setArticleId(dto.getArticleId());
            apReadBehavior.setPercentage(dto.getPercentage());
            apReadBehavior.setEntryId(apBehaviorEntry.getId());
            apReadBehavior.setLoadDuration(dto.getLoadDuration());
            apReadBehavior.setReadDuration(dto.getReadDuration());
            apReadBehavior.setCreatedTime(new Date());
            save(apReadBehavior);
        }else {
            apReadBehavior.setUpdatedTime(new Date());
            apReadBehavior.setCount((short)(apReadBehavior.getCount()+1));
            updateById(apReadBehavior);
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}
