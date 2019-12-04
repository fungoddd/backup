package com.crrcdt.backup.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用于验证重复返回值
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日09:27:41
 */
@Data
class ResultValidator implements Serializable {

    private String errorMsg;

    ResultValidator(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
