package com.crrcdt.backup.controller;

import com.crrcdt.backup.api.UserService;
import com.crrcdt.backup.common.base.BaseController;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.validator.StringBlankValidator;
import com.crrcdt.backup.common.validator.Valid;
import com.crrcdt.backup.jwt.AuthService;
import com.crrcdt.backup.jwt.JwtAuthenticationResponse;
import com.crrcdt.backup.jwt.JwtTokenUtil;
import com.crrcdt.backup.model.UserInfo;
import com.crrcdt.backup.utils.RequestParameterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 登录管理Controller
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日09:15:30
 */
@Slf4j
@RestController
@RequestMapping("/v1/backup/sso")
@Api(value = "登录管理", description = "登录管理")
public class SsoController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    /**
     * <p>登录</p>
     *
     * @param user 用户信息
     * @return Object
     */
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody UserInfo user) {
        Valid valid = new Valid().on(user.getUsername(), new StringBlankValidator("帐号")).on(user.getPassword(), new StringBlankValidator("密码"));
        if (valid.isError()) {
            return valid.errorInfo();
        }
        JwtAuthenticationResponse response = authService.login(user.getUsername(), user.getPassword());
        return response.isSuccess()
                ? new BaseResult(BaseResult.SUCCESS, "登录成功！", response.getToken())
                : new BaseResult(BaseResult.FAILED, "登录失败 " + response.getMessage(),
                response.getMessage());
    }

    /**
     * <p>退出登录</p>
     *
     * @return Object
     */
    @ApiOperation(value = "退出登录")
    @GetMapping(value = "/logout")
    public Object logout(HttpServletRequest request) {
        String authToken = RequestParameterUtil.getToken(request);
        String userId = JwtTokenUtil.getUserIdFromToken(authToken);
        return BaseResult.successResultCreate("退出登录!");
    }

}
