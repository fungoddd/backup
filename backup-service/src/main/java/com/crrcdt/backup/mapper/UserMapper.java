package com.crrcdt.backup.mapper;

import com.crrcdt.backup.common.base.BaseMapper;
import com.crrcdt.backup.model.UserInfo;

/**
 * <p>
 * 用户管理Mapper接口
 * </p>
 *
 * @author LiuYuHang
 * @date 2019年11月6日13:00:59
 */
public interface UserMapper extends BaseMapper<UserInfo> {

    /**
     * <p>登录</p>
     *
     * @param username 用户登录名
     * @return User
     */
    UserInfo login(String username);

}
