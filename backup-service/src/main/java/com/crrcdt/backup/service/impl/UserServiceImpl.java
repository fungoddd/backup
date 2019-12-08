package com.crrcdt.backup.service.impl;

import com.crrcdt.backup.api.UserService;
import com.crrcdt.backup.common.base.BaseServiceImpl;
import com.crrcdt.backup.mapper.UserMapper;
import com.crrcdt.backup.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户管理接口实现类
 * </p>
 *
 * @author lyh
 * @date 2019年11月6日12:58:55
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserInfo, Map<String, Object>> implements UserService {

    /**
     * <p>登录</p>
     *
     * @param username 用户登录名
     * @return User
     */
    @Override
    public UserInfo login(String username) {
        if (StringUtils.isNotBlank(username)) {
            return baseMapper.login(username);
        }
        return null;
    }

}
