package com.crrcdt.backup.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.crrcdt.backup.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author lyh
 * @date 2019年10月31日16:01:56
 */
@Data
@TableName(value = "user_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户实体类")
public class UserInfo extends BaseModel {

    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 是否可登录(0:不可登录;1:可登陆)
     */
    @TableField(value = "login_flag")
    @ApiModelProperty(value = "是否可登录(0:不可登录;1:可登陆)")
    private String loginFlag;

    /**
     * 最后登陆IP
     */
    @TableField(value = "login_ip")
    @ApiModelProperty(value = "最后登陆IP")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @TableField(value = "login_date")
    @ApiModelProperty(value = "最后登陆时间")
    private Date loginDate;

}
