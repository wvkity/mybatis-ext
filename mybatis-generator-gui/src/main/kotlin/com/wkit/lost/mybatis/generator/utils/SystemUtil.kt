package com.wkit.lost.mybatis.generator.utils;

/**
 * 系统工具
 * @author wvkity
 */
class SystemUtil {

    companion object {
        /**
         * 获取用户主目录
         * @return 目录地址
         */
        fun userHome(): String {
            return System.getProperties().getProperty("user.home")
        }

        fun userDir(): String {
            return System.getProperties().getProperty("user.dir")
        }
    }
}
