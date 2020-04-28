package com.suxia.cc.mybatis.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/27 20:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -5104914005343951117L;

    @NotBlank(message = "姓名不能为空")
    private String name;
}
