package com.crrcdt.backup.mapper;

import com.crrcdt.backup.common.base.BaseMapper;
import com.crrcdt.backup.model.User;

/**
 * <p>
 * 用户管理Mapper接口
 * </p>
 *
 * @author lyh
 * @date 2019年11月6日13:00:59
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * <p>登录</p>
     *
     * @param username 用户登录名
     * @return User
     */
    User login(String username);

}
