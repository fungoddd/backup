package com.crrcdt.backup.common.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * String 工具类
 * Created by crrcdt on 2016/12/07.
 */
public class ThreadLocalUtil {


    private final static ThreadLocal<String> tenantId = new ThreadLocal<>();
    private final static ThreadLocal<String> currentUserId = new ThreadLocal<>();
    private final static ThreadLocal<String> module = new ThreadLocal<>();
    /**
     * <p>
     * 未经过Dubbo传递不可跨模块调用,只能在Sys模块使用(SysClient)
     * </p>
     */
    private final static ThreadLocal<String> roleEnNames = new ThreadLocal<>();
    /**
     * 是否启用数据范围（针对EntityWrapper方式）
     * 查询特定人员数据筛选范围，如果 user为空，则跳过 数据筛选范围，查询全部数据
     */
    private final static ThreadLocal<String> dataScopeUserId = new ThreadLocal<>();

    public static ThreadLocal<String> getThreadLocalTenantId() {
        return tenantId;
    }

    public static ThreadLocal<String> getThreadLocalCurrentUserId() {
        return currentUserId;
    }

    public static ThreadLocal<String> getThreadLocalModule() {
        return module;
    }

    /**
     * <p>
     *  未经过Dubbo传递不可跨模块调用,只能在Sys模块使用(SysClient)
     * </p>
     *
     * @return ThreadLocal<String> roleEnNames
     */
    public static ThreadLocal<String> getThreadLocalRoleEnNames() {
        return roleEnNames;
    }

    public static String getCurrentUserId() {

        if (StringUtils.isBlank(currentUserId.get())) {
            // 避免返回null
            return "";
        }
        return currentUserId.get();
    }

    public static String getTenantId() {
        if (StringUtils.isBlank(tenantId.get())) {
            // 避免返回null
            return "";
        }
        return tenantId.get();
    }

    public static String getModule() {
        return StringUtils.isBlank(module.get()) ? "" : module.get();
    }

    public static String getRoleEnNames() {
        return StringUtils.isBlank(roleEnNames.get()) ? "" : roleEnNames.get();
    }

    public static ThreadLocal<String> getThreadLocalDataScopeUserId() {
        return dataScopeUserId;
    }

    public static String getDataScopeUserId() {
        return StringUtils.isBlank(dataScopeUserId.get()) ? "" : dataScopeUserId.get();
    }

}

