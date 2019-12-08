package com.crrcdt.backup.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.crrcdt.backup.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 文件备份计划实体类
 * </p>
 *
 * @author lyh
 * @date 2019年10月30日15:42:41
 */
@Data
@TableName(value = "backup")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文件备份计划实体类")
public class Backup extends BaseModel {

    /**
     * 备份目录或文件的来源路径
     */

    @ApiModelProperty("备份目录或文件的源路径")
    private String sourceDir;

    /**
     * 备份目录或文件的目标路径
     */
    @ApiModelProperty("备份目录或文件的目标路径")
    private String targetDir;

    /**
     * 是否开启定时备份(0:不开启,1:开启)
     */
    @ApiModelProperty("是否开启定时备份(0:不开启;1:开启)")
    private String scheduledFlag;

    /**
     * 定时任务周期
     */
    @ApiModelProperty("定时任务周期")
    private String scheduledCycle;

    /**
     * 备份结果标记(0:备份失败;1:备份成功)
     */
    @ApiModelProperty("(备份结果标记0:备份失败;1:备份成功)")
    private String backupState;

    /**
     * 备份开始时间
     */
    @ApiModelProperty("备份开始时间")
    private Date backupStartTime;

    /**
     * 备份结束时间
     */
    @ApiModelProperty("备份结束时间")
    private Date backupEndTime;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 文件服务器id
     */
    @ApiModelProperty("文件服务器id")
    private String serverId;

}
