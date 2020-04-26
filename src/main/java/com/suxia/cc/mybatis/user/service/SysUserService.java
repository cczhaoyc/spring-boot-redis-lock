package com.suxia.cc.mybatis.user.service;

import com.suxia.cc.mybatis.user.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cczhaoyc@163.com
 * @since 2020-04-26
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByName(String name);

}
