package com.wvkity.mybatis.code.make.config;

import com.wvkity.mybatis.code.make.utils.FileUtil;
import com.wvkity.mybatis.code.make.utils.SystemUtil;
import lombok.extern.log4j.Log4j2;

import java.io.File;

/**
 * 系统配置
 * @author wvkity
 */
@Log4j2
public final class SystemConfiguration {

    private SystemConfiguration() {
    }

    /**
     * 根目录
     */
    private static final String BASIC_DIR = SystemUtil.USER_HOME + FileUtil.FILE_SEPARATOR + ".mybatis_generator";

    /**
     * 本地配置文件名称
     */
    private static final String LOCAL_CONFIG_FILE = "mybatis_generator.properties";
    /**
     * 本地数据库
     */
    private static final String LOCAL_DATABASE = "configuration.db";
    /**
     * 本地配置文件目录
     */
    private static final String LOCAL_CONFIG_DIR = BASIC_DIR + FileUtil.FILE_SEPARATOR + "config";
    /**
     * 本地数据库文件目录
     */
    private static final String LOCAL_DATABASE_DIR = LOCAL_CONFIG_DIR + FileUtil.FILE_SEPARATOR + "db";
    /**
     * 本地日志文件
     */
    private static final String LOCAL_LOG_DIR = BASIC_DIR + FileUtil.FILE_SEPARATOR + "logs";
    /**
     * 全局配置文件路径
     */
    private static final String LOCAL_GLOBAL_CONFIG_FILE = LOCAL_CONFIG_DIR + FileUtil.FILE_SEPARATOR +
            LOCAL_CONFIG_FILE;
    /**
     * jar文件目录下的配置文件路径
     */
    private static final String LOCAL_SPECIFY_CONFIG_FILE = SystemUtil.USER_DIR + FileUtil.FILE_SEPARATOR +
            LOCAL_CONFIG_FILE;
    /**
     * 数据库路径
     */
    private static final String LOCAL_DATABASE_URL = LOCAL_DATABASE_DIR + FileUtil.FILE_SEPARATOR + LOCAL_DATABASE;

    /**
     * 获取系统默认配置文件
     * @return 默认配置文件路径
     */
    public static String getSystemConfigFile() {
        return LOCAL_CONFIG_FILE;
    }

    /**
     * 获取本地全局配置文件
     * @return 全局配置文件路径
     */
    public static String getGlobalConfigFile() {
        return LOCAL_GLOBAL_CONFIG_FILE;
    }

    /**
     * 获取本地指定配置文件
     * @return 配置文件路径
     */
    public static String getSpecifyConfigFile() {
        return LOCAL_SPECIFY_CONFIG_FILE;
    }

    /**
     * 获取本地数据库路径
     * @return 数据库路径
     */
    public static String getLocalDatabaseUrl() {
        return LOCAL_DATABASE_URL;
    }

    /**
     * 初始化配置
     */
    public static void init() {
        try {
            if (!FileUtil.exists(LOCAL_CONFIG_DIR)) {
                final ClassLoader loader = Thread.currentThread().getContextClassLoader();
                log.info("Initializing system configuration...");
                // 创建本地数据库
                FileUtil.touch(new File(LOCAL_DATABASE_URL), loader.getResourceAsStream(LOCAL_DATABASE));
                // 创建全局配置文件
                FileUtil.touch(new File(LOCAL_GLOBAL_CONFIG_FILE), loader.getResourceAsStream(LOCAL_CONFIG_FILE));
                log.info("System configuration initialization succeeded.");
            }
        } catch (Exception e) {
            log.error("Initialization configuration failed: ", e);
        }
    }
}
