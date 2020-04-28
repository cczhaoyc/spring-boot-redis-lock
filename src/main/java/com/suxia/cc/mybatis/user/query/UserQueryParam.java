package com.suxia.cc.mybatis.user.query;

import com.suxia.cc.mybatis.base.query.AbstractQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/27 15:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQueryParam extends AbstractQueryParam {

    private static final long serialVersionUID = 8476922667483608254L;

    private String name;

}
