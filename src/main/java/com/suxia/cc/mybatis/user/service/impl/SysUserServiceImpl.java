package com.suxia.cc.mybatis.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suxia.cc.mybatis.base.constant.DefaultConstants;
import com.suxia.cc.mybatis.base.exception.ServiceException;
import com.suxia.cc.mybatis.base.result.PageResult;
import com.suxia.cc.mybatis.user.domain.SysUser;
import com.suxia.cc.mybatis.user.dto.UserDTO;
import com.suxia.cc.mybatis.user.mapper.SysUserMapper;
import com.suxia.cc.mybatis.user.query.UserQueryParam;
import com.suxia.cc.mybatis.user.service.SysUserService;
import com.suxia.cc.mybatis.user.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cczhaoyc@163.com
 * @since 2020-04-26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getByName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new ServiceException("姓名不能为空");
        }
        try {
//            return sysUserMapper.getByName(name);
            int a = 1 / 0;
            return null;
        } catch (Exception e) {
            throw new ServiceException(1000, "通过姓名获取用户信息异常", e);
        }
    }

    @Override
    public PageResult<SysUser> pageByName(UserQueryParam queryParam) {
        // 分页查询
        if (queryParam.getPageSize() == 0) {
            queryParam.setPageSize(DefaultConstants.DEFAULT_PAGE_SIZE);
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getName, queryParam.getName());

        IPage<SysUser> iPage = new Page<>(queryParam.getPageNo(), queryParam.getPageSize());
        IPage<SysUser> page = this.page(iPage, wrapper);
        return PageResult.create(queryParam, page.getRecords(), page.getTotal());
    }

    @Override
    public UserVO getByName(UserDTO userDTO) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getName, userDTO.getName());
        try {
            SysUser one = this.getOne(wrapper);
            UserVO vo = new UserVO();
            BeanCopier copier = BeanCopier.create(SysUser.class, UserVO.class, false);
            copier.copy(one, vo, null);
            return vo;
        } catch (Exception e) {
            throw new ServiceException(1000, "通过姓名获取用户信息异常", e);
        }
    }


}
