package com.crrcdt.backup.controller;

import com.crrcdt.backup.common.base.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户管理Controller
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日09:13:29
 */
@RestController
@RequestMapping("/v1/backup/user")
@Api(value = "用户管理", description = "用户管理")
public class UserController extends BaseController {

}
