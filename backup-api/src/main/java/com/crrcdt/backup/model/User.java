package com.crrcdt.backup.model;

import com.crrcdt.backup.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author lyh
 * @date 2019年10月31日16:01:56
 */
@ApiModel(value = "用户实体类")
@Data
public class User extends BaseModel {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 是否可登录(0:不可登录;1:可登陆)
     */
    @ApiModelProperty(value = "是否可登录(0:不可登录;1:可登陆)")
    private String loginFlag;

    /**
     * 最后登陆IP
     */
    @ApiModelProperty(value = "最后登陆IP")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @ApiModelProperty(value = "最后登陆时间")
    private Date loginDate;
}
