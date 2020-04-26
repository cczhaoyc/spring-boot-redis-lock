package com.suxia.cc.mybatis.user.mapper;

import com.suxia.cc.mybatis.user.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author cczhaoyc@163.com
 * @since 2020-04-26
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getByName(@Param("name") String name);

}
