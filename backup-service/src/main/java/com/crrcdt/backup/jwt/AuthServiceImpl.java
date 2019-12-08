package com.crrcdt.backup.jwt;

import com.crrcdt.backup.api.UserService;
import com.crrcdt.backup.common.constant.BackupConstant;
import com.crrcdt.backup.common.utils.EncryUtil;
import com.crrcdt.backup.common.utils.RequestUtil;
import com.crrcdt.backup.utils.HttpSessionIdHolder;
import com.crrcdt.backup.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * <p>Token登录实现类</p>
 * @author lyh
 * @date 2019年11月6日11:02:46
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

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
        UserInfo user = userService.login(username);
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
            // 获取request
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = Objects.requireNonNull(servletRequestAttributes).getRequest();
            //获取当前账号登录的ip地址
            String loginIp = RequestUtil.getIpAddr(request);
            user.setLoginDate(new Date());
            user.setLoginIp(loginIp);
            userService.updateSelective(user);
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
        UserInfo user = (UserInfo) userService.getById(userId).getData();
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
        UserInfo info = (UserInfo) userService.getById(userId).getData();
        return info.getId().equals(userId);
    }

}
