package com.wvkity.mybatis.code.make.utils;

/**
 * 系统工具
 * @author wvkity
 */
public final class SystemUtil {

    private SystemUtil() {
    }

    /**
     * 用户主目录
     */
    public static final String USER_HOME = System.getProperty("user.home");

    /**
     * 当前程序所在目录
     */
    public static final String USER_DIR = System.getProperty("user.dir");
}
