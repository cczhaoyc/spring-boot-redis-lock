package com.suxia.cc.mybatis.user.controller;


import com.suxia.cc.mybatis.user.domain.SysUser;
import com.suxia.cc.mybatis.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cczhaoyc@163.com
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/add")
    public String add(SysUser user) {
        sysUserService.save(user);
        return "Success";
    }

    @GetMapping("/get")
    public SysUser add(Long id) {
        return sysUserService.getById(id);
    }

    @GetMapping("/get")
    public SysUser add(String name) {
        return sysUserService.getByName(name);
    }

}
