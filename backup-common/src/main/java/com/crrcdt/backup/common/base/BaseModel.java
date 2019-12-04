package com.crrcdt.backup.common.base;

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
    @ApiModelProperty("主键id")
    protected String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    protected String name;

    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记(0:正常;1:删除)")
    protected String delFlag;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    protected String remarks;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    protected Date createDate;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    protected String createBy;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    protected String updateBy;

    /**
     * 更新时间
     */
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
