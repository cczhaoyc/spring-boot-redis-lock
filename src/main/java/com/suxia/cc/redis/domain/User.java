package com.suxia.cc.redis.domain;

import java.io.Serializable;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/22 12:44
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7512115895509187986L;
    private String name;
    private Integer age;
    private String birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
