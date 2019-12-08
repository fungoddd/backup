package com.crrcdt.backup.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 基础属性实体类
 * </p>
 *
 * @author lyh
 * @date 2019年10月31日15:41:16
 */
@Data
@SuppressWarnings("serial")
@ApiModel(value = "基础属性实体类")
public abstract class BaseModel implements Serializable, Cloneable {

    /**
     * 主键
     */
    @TableField(value = "id")
    @ApiModelProperty("主键id")
    protected String id;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty("名称")
    protected String name;

    /**
     * 删除标记
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "删除标记(0:正常;1:删除)")
    protected String delFlag;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    @ApiModelProperty(value = "备注")
    protected String remarks;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建者")
    protected String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    @ApiModelProperty(value = "创建时间")
    protected Date createDate;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    protected String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_date")
    @ApiModelProperty(value = "更新时间")
    protected Date updateDate;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
