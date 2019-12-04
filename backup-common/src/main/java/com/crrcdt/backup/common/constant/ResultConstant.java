package com.crrcdt.backup.common.constant;

import lombok.Getter;

/**
 * <p>
 * 接口返回结果常量枚举
 * </p>
 *
 * @author lyh
 * @date 2019年10月30日15:54:24
 */
@Getter
public enum ResultConstant {

    FAILED(0, "操作失败!" ),
    SUCCESS(1, "操作成功!" ),
    VALIDATOR(2, "输入错误!" );

    private int code;

    private String message;

    ResultConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
