package com.crrcdt.backup.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.crrcdt.backup.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件服务器实体类
 * </p>
 *
 * @author lyh
 * @date 2019年10月31日16:04:33
 */
@Data
@TableName(value = "server")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文件服务器实体类")
public class Server extends BaseModel {

    /**
     * 服务器登录用户
     */
    @ApiModelProperty("服务器登录用户")
    private String user;

    /**
     * 服务器用户密码
     */
    @ApiModelProperty("服务器用户密码")
    private String password;

    /**
     * 服务器ip地址
     */
    @ApiModelProperty("服务器ip地址")
    private String host;

    /**
     * 服务器连接端口
     */
    @ApiModelProperty("服务器连接端口")
    private Integer port;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;

}
