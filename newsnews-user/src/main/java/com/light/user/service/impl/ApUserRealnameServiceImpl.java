package com.light.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.common.constants.user.UserConstants;
import com.light.model.article.pojos.ApAuthor;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.media.pojos.WmUser;
import com.light.model.user.dtos.AuthDto;
import com.light.model.user.pojos.ApUser;
import com.light.model.user.pojos.ApUserRealname;
import com.light.user.feign.ArticleFeign;
import com.light.user.feign.WemediaFeign;
import com.light.user.mapper.ApUserMapper;
import com.light.user.mapper.ApUserRealnameMapper;
import com.light.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户认证列表业务实现类
 *
 * @author houhai
 */
@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {

    /**
     * 根据状态查询需要认证相关的用户信息
     *
     * @param dto
     * @return
     */
    @Override
    public PageResponseResult loadListByStatus(AuthDto dto) {
        //参数为空。直接返回错误提示
        if (dto == null) {
            return (PageResponseResult) PageResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //检查参数
        dto.checkParam();
        QueryWrapper<ApUserRealname> wrapper = new QueryWrapper<>();
        if (dto.getStatus() != null) {
            wrapper.lambda().eq(ApUserRealname::getStatus, dto.getStatus());
        }

        IPage pageParam = new Page(dto.getPage(), dto.getSize());
        IPage page = page(pageParam, wrapper);

        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setCode(200);
        responseResult.setData(page.getRecords());

        return responseResult;
    }

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private WemediaFeign wemediaFeign;

    @Autowired
    private ApUserMapper apUserMapper;

    /**
     * 用户审核
     * @param dto
     * @param status
     * @return
     */
    @Transactional
    @Override
    public ResponseResult updateStatusById(AuthDto dto, Short status) {

        //检查参数
        if (dto == null || status == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if (statusCheck(status)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //修改状态
        ApUserRealname apUserRealname = new ApUserRealname();
        apUserRealname.setId(dto.getId());
        apUserRealname.setStatus(status);
        if (dto.getMsg() != null) {
            apUserRealname.setReason(dto.getMsg());
        }
        updateById(apUserRealname);

        //认证通过添加自媒体账号和作者账号
        //判断传入的用户审核信息是否通过
        if(status.equals(UserConstants.PASS_AUTH)){
            ResponseResult createResult = createWmUserAndAuthor(dto);
            if (createResult != null) {
                return createResult;
            }
            //TODO 发送通知消息
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 创建自媒体账号， 以及作者账号
     * @param dto
     * @return
     */
    public ResponseResult createWmUserAndAuthor(AuthDto dto){
        //添加自媒体账号，查询ap_user表中的信息封装到wmUser中
        ApUserRealname aur = getById(dto.getId());

        ApUser apUser = apUserMapper.selectById(aur.getUserId());

        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //检测自媒体用户是否存在
        WmUser wmUser = wemediaFeign.findByName(apUser.getName());
        //如果没有则给自媒体用户的字段赋值
        if (wmUser == null) {
            wmUser = new WmUser();
            wmUser.setApUserId(apUser.getId());
            wmUser.setCreatedTime(new Date());
            wmUser.setSalt(apUser.getSalt());
            wmUser.setName(apUser.getName());
            wmUser.setPassword(apUser.getPassword());
            wmUser.setStatus(9);
            wmUser.setPhone(apUser.getPhone());
            wemediaFeign.save(wmUser);
        }
        //创建作者账号
        createAuthor(wmUser);
        //修改ap_user标记
        Short flag = 1;
        apUser.setFlag(flag);
        apUserMapper.updateById(apUser);
        return null;
    }

    /**
     * 创建自媒体账号
     * @param wmUser
     */
    private void createAuthor(WmUser wmUser){
        //获得用户ID
        Integer apUserId = wmUser.getApUserId();
        //通过feign调用自媒体作者模块通过id查询账号
        ApAuthor apAuthor = articleFeign.findByUserId(apUserId);
        //如果账号为空，则表示该用户没有注册，为其APP用户注册自媒体作者账号
        if (apAuthor == null) {
            apAuthor = new ApAuthor();
            apAuthor.setName(wmUser.getName());
            apAuthor.setType(UserConstants.AUTH_TYPE_SELF);
            apAuthor.setCreatedTime(new Date());
            apAuthor.setUserId(apUserId);
            articleFeign.save(apAuthor);
        }
    }

    /**
     * 状态监测
     * @param status
     * @return
     */
    public boolean statusCheck(Short status) {
        if (status == null || (!status.equals(UserConstants.FAIL_AUTH) && !status.equals(UserConstants.PASS_AUTH))) {
            return true;
        }
        return false;
    }

}
