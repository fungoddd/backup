package com.crrcdt.backup.controller;

import com.crrcdt.backup.api.UserService;
import com.crrcdt.backup.common.base.BaseController;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.constant.BackupConstant;
import com.crrcdt.backup.common.utils.EncryUtil;
import com.crrcdt.backup.common.validator.NotNullValidator;
import com.crrcdt.backup.common.validator.Valid;
import com.crrcdt.backup.model.UserInfo;
import com.crrcdt.backup.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户管理Controller
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日09:13:29
 */
@Slf4j
@RestController
@RequestMapping("/v1/backup/user")
@Api(value = "用户管理", description = "用户管理")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * <p>新增用户信息</p>
     *
     * @param user 用户对象
     * @return Object
     */
    @ApiOperation(value = "新增用户信息", response = UserInfo.class)
    @PostMapping("/")
    public Object add(@RequestBody UserInfo user) {
        Valid valid = new Valid().on(user.getUsername(), new NotNullValidator("用户名"))
                .on(user.getPassword(), new NotNullValidator("密码"));
        if (valid.isError()) {
            return valid.errorInfo();
        }
        String userId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            return BaseResult.failResultCreate("添加用户信息失败!请先登录!");
        }
        if (!userId.equalsIgnoreCase(BackupConstant.SUPPER_ADMIN_ID)) {
            return BaseResult.failResultCreate("添加用户信息失败!权限不足!");
        }
        user.setPassword(EncryUtil.entryptPassword(user.getPassword()));
        return userService.add(user);
    }

    /**
     * <p>通过主键查询用户信息</p>
     *
     * @param userId 主键
     * @return 用户对象
     */
    @ApiOperation(value = "查询用户信息", response = UserInfo.class)
    @GetMapping("/")
    public Object get(@ApiParam(required = true, value = "用户主键") @RequestParam(value = "userId") String userId) {
        String currentUserId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(currentUserId)) {
            return BaseResult.failResultCreate("获取用户信息失败!请先登录!");
        }
        return userService.getById(userId);
    }

    /**
     * <p>获取当前登录用户信息</p>
     *
     * @return 用户对象
     */
    @ApiOperation(value = "查询当前用户信息", response = UserInfo.class)
    @GetMapping("/current")
    public Object getCurrentUser() {
        String currentUserId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(currentUserId)) {
            return BaseResult.failResultCreate("获取用户信息失败!请先登录!");
        }
        return userService.getById(currentUserId);
    }

    /**
     * <p>查询用户信息列表</p>
     *
     * @return 用户信息列表
     */
    @ApiOperation(value = "查询用户信息列表", response = UserInfo.class)
    @GetMapping("/list")
    public Object list() {
        String userId = WebUtil.getCurrentUserId();
        if (StringUtils.isBlank(userId)) {
            return BaseResult.failResultCreate("获取用户信息失败!请先登录!");
        }
        if (!userId.equalsIgnoreCase(BackupConstant.SUPPER_ADMIN_ID)) {
            return BaseResult.failResultCreate("获取用户信息失败!权限不足!");
        }
        return BaseResult.successResultCreate(userService.list());
    }

    /**
     * <p>修改用户信息</p>
     *
     * @param user 用户对象
     * @return Object
     */
    @ApiOperation(value = "修改用户信息")
    @PutMapping("/")
    public Object update(@RequestBody UserInfo user) {
        Valid valid = new Valid().on(user.getUsername(), new NotNullValidator("用户名"))
                .on(user.getPassword(), new NotNullValidator("密码"));
        if (valid.isError()) {
            return valid.errorInfo();
        }
        return null;
    }

    /**
     * <p>通过主键物理删除用户信息</p>
     *
     * @param userId 用户主键
     * @return Object
     */
    @ApiOperation(value = "物理删除用户信息")
    @DeleteMapping("/")
    public Object delete(@ApiParam(required = true, value = "用户主键") @RequestParam(value = "userId") String userId) {
        return userService.deleteById(userId);
    }

}
