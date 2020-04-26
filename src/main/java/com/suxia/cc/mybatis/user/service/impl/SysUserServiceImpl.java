package com.suxia.cc.mybatis.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suxia.cc.mybatis.user.domain.SysUser;
import com.suxia.cc.mybatis.user.mapper.SysUserMapper;
import com.suxia.cc.mybatis.user.service.SysUserService;
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
        return sysUserMapper.getByName(name);
    }


}
