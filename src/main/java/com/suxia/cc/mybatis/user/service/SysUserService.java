package com.suxia.cc.mybatis.user.service;

import com.suxia.cc.mybatis.base.result.PageResult;
import com.suxia.cc.mybatis.user.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suxia.cc.mybatis.user.dto.UserDTO;
import com.suxia.cc.mybatis.user.query.UserQueryParam;
import com.suxia.cc.mybatis.user.vo.UserVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cczhaoyc@163.com
 * @since 2020-04-26
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据姓名查询用户信息
     */
    SysUser getByName(String name);

    /**
     * 分页查询用户信息
     */
    PageResult<SysUser> pageByName(UserQueryParam queryParam);

    /**
     * 根据姓名查询用户信息
     */
    UserVO getByName(UserDTO userDTO);

}
