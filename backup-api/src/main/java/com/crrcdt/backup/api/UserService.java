package com.crrcdt.backup.api;

import com.crrcdt.backup.common.base.BaseService;
import com.crrcdt.backup.model.UserInfo;

import java.util.Map;

/**
 * <p>
 * 用户管理接口
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日09:32:33
 */
public interface UserService extends BaseService<UserInfo, Map<String, Object>> {

    /**
     * <p>登录</p>
     *
     * @param username 用户登录名
     * @return User
     */
    UserInfo login(String username);

}
