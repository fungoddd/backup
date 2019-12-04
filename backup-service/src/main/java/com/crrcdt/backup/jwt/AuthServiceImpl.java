package com.crrcdt.backup.jwt;

import com.crrcdt.backup.api.UserService;
import com.crrcdt.backup.common.constant.BackupConstant;
import com.crrcdt.backup.common.utils.EncryUtil;
import com.crrcdt.backup.utils.HttpSessionIdHolder;
import com.crrcdt.backup.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Token登录实现类</p>
 * @author lyh
 * @date 2019年11月6日11:02:46
 */
@Service
public class AuthServiceImpl implements AuthService {


    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserService userService;

    /**
     * <p>登录</p>
     * @param username 用户登录名
     * @param password 用户密码
     * @return JwtAuthenticationResponse
     */
    @Override
    public JwtAuthenticationResponse login(String username, String password) {
        String token;
        User user = userService.login(username);
        if (user == null) {
            return new JwtAuthenticationResponse(false, "帐号不存在!");
        }

        if (BackupConstant.DelFlag.DELETED.equals(user.getDelFlag())) {
            return new JwtAuthenticationResponse(false, "帐号已被删除!");
        }

        if (!BackupConstant.ONE.equals(user.getLoginFlag())) {
            return new JwtAuthenticationResponse(false, "该帐号已被禁止登陆,请联系管理员.");
        }
        if (EncryUtil.validatePassword(password, user.getPassword())) {
            token = JwtTokenUtil.generateToken(user);
            //设置sessionId
            HttpSessionIdHolder.setSessionId(JwtTokenUtil.getSessionIdFromToken(token));
            return new JwtAuthenticationResponse(token);
        } else {
            return new JwtAuthenticationResponse(false, "密码错误!");
        }
    }

    /**
     * <p>刷新token</p>
     *
     * @param token token
     * @return token
     */
    @Override
    public String refresh(String token) {
        String userId = JwtTokenUtil.getUserIdFromToken(token);
        User user = userService.getByPK(userId);
        if (JwtTokenUtil.canTokenBeRefreshed(token, user.getUpdateDate())) {
            return JwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    /**
     * <p>token校验</p>
     *
     * @param token    token
     * @param resource resource
     * @return Boolean
     */
    @Override
    public Boolean validate(String token, String resource) {
        String userId = JwtTokenUtil.getUserIdFromToken(token);
        User info = userService.getByPK(userId);
        return info.getId().equals(userId);
    }

}
