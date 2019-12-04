package com.crrcdt.backup.controller;

import com.crrcdt.backup.api.UserService;
import com.crrcdt.backup.common.base.BaseController;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.validator.StringBlankValidator;
import com.crrcdt.backup.common.validator.Valid;
import com.crrcdt.backup.jwt.AuthService;
import com.crrcdt.backup.jwt.JwtAuthenticationResponse;
import com.crrcdt.backup.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 登录管理Controller
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日09:15:30
 */
@RestController
@RequestMapping("/v1/user")
@Api(value = "登录管理", description = "登录管理")
public class SsoController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(SsoController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    /**
     *<p>登录</p>
     * @param user 用户信息
     * @return Object
     */
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody User user) {
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

}
