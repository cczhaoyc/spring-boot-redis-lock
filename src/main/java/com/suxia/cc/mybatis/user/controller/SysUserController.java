package com.suxia.cc.mybatis.user.controller;


import com.suxia.cc.mybatis.user.domain.SysUser;
import com.suxia.cc.mybatis.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get/id/{id}")
    public SysUser getById(@PathVariable("id") Long id) {
        return sysUserService.getById(id);
    }

    @GetMapping("/get/name/{name}")
    public SysUser getByName(@PathVariable("name") String name) {
        return sysUserService.getByName(name);
    }

}
