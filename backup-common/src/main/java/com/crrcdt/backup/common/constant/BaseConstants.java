package com.crrcdt.backup.common.constant;

/**
 * <p>
 * 全局常量
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日13:10:58
 */
public class BaseConstants {

    /**
     * 逻辑删除标记（0：正常，1：删除）
     */
    public static final String DEL_FLAG = "del_flag";

    /**
     * 删除标识
     * 删除标记（0：正常；1：删除）
     */
    public static class DelFlag {
        public static final String NORMAL = "0";//
        public static final String DELETED = "1";//

    }

    /**
     * 判断真假 1真 0假
     */
    public static class IsFlag {
        public static final String isTrue = "1";
        public static final String isFalse = "0";
    }

}
