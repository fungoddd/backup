package com.crrcdt.backup.jwt;

/**
 * <p>Token登录接口</p>
 *
 * @author LiuYuHang
 * @date 2019年11月6日11:54:17
 */
public interface AuthService {

    /**
     * <p>登录</p>
     *
     * @param username 用户登录名
     * @param password 用户密码
     * @return JwtAuthenticationResponse
     */
    JwtAuthenticationResponse login(String username, String password);

    /**
     * <p>刷新token</p>
     *
     * @param oldToken oldToken
     * @return newToken
     */
    String refresh(String oldToken);

    /**
     * <p>token验证</p>
     *
     * @param token    token
     * @param resource resource
     * @return Boolean
     */
    Boolean validate(String token, String resource);

}
