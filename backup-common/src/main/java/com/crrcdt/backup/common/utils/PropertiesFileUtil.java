package com.crrcdt.backup.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <p>
 * 资源文件读取工具
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日12:03:35
 */
public class PropertiesFileUtil {

    /**
     * 默认资源文件名称
     */
    private static final String NAME = "config";

    /**
     * 缓存时间
     */
    private static final Integer TIME_OUT = 60 * 1000;

    /**
     * 当打开多个资源文件时,缓存资源文件
     */
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap<>();

    /**
     * 打开文件时间,判断超时使用
     */
    private Date loadTime;

    /**
     * 资源文件
     */
    private ResourceBundle resourceBundle;

    /**
     * 私有构造方法，创建单例
     *
     * @param name name
     */
    private PropertiesFileUtil(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesFileUtil getInstance(String name) {
        PropertiesFileUtil conf = configMap.get(name);
        if (null == conf) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        // 判断是否打开的资源文件是否超时1分钟
        if ((System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        return conf;
    }

    /**
     * 根据key读取value
     *
     * @param key
     * @return value
     */
    public String get(String key) {
        try {
            String value = resourceBundle.getString(key);
            return value;
        } catch (MissingResourceException e) {
            return "";
        }
    }

    /**
     * 根据key读取value(整型)
     *
     * @param key
     * @return value
     */
    public Integer getInt(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key读取value(布尔)
     *
     * @param key
     * @return value
     */
    public boolean getBool(String key) {
        try {
            String value = resourceBundle.getString(key);
            if ("true".equals(value)) {
                return true;
            }
            return false;
        } catch (MissingResourceException e) {
            return false;
        }
    }

    private Date getLoadTime() {
        return loadTime;
    }

}
