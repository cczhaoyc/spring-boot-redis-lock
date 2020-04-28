package com.suxia.cc.mybatis.user.controller;


import com.suxia.cc.mybatis.base.result.ActionResult;
import com.suxia.cc.mybatis.user.domain.SysUser;
import com.suxia.cc.mybatis.user.dto.UserDTO;
import com.suxia.cc.mybatis.user.query.UserQueryParam;
import com.suxia.cc.mybatis.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ActionResult add(SysUser user) {
        sysUserService.save(user);
        return ActionResult.success(user);
    }

    @GetMapping("/get/id/{id}")
    public ActionResult getById(@PathVariable("id") Long id) {
        return ActionResult.success(sysUserService.getById(id));
    }

    @GetMapping("/get/name/{name}")
    public ActionResult getByName(@PathVariable("name") String name) {
        return ActionResult.success(sysUserService.getByName(name));
    }

    @PostMapping("/page/name")
    public ActionResult pageByName(UserQueryParam queryParam) {
        return ActionResult.success(sysUserService.pageByName(queryParam));
    }

    @GetMapping("/get/userDTO")
    public ActionResult get(@Valid UserDTO userDTO) {
        return ActionResult.success(sysUserService.getByName(userDTO));
    }

}
