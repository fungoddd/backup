package com.crrcdt.backup.utils;

/**
 * <p>
 * Session操作工具类
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日11:50:36
 */
public final class HttpSessionIdHolder {

    private final static ThreadLocal<String> SESSION_ID = new ThreadLocal<>();

    private HttpSessionIdHolder() {
    }

    /**
     * 获取sessionId
     *
     * @return sessionId
     */
    public static String getSessionId() {
        return SESSION_ID.get();
    }

    /**
     * 设置sessionId
     *
     * @param sessionId sessionIdd
     */
    public static void setSessionId(String sessionId) {
        SESSION_ID.set(sessionId);
    }

    /**
     * 删除sessionId
     */
    public static void removeSessionId() {
        SESSION_ID.remove();
    }

}