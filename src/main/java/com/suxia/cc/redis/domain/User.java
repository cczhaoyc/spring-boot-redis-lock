package com.suxia.cc.redis.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/22 12:44
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 7512115895509187986L;

    private String name;
    private Integer age;
    private String birthday;

}
