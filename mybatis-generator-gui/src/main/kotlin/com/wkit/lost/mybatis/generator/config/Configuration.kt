package com.wkit.lost.mybatis.generator.config

import com.wkit.lost.mybatis.generator.utils.FileUtil
import com.wkit.lost.mybatis.generator.utils.SystemUtil
import org.apache.logging.log4j.LogManager

/**
 * 配置工具类
 * @author wvkity
 */
class Configuration {

    companion object {

        private val LOG = LogManager.getLogger(Configuration)

        /**
         * 根目录
         */
        private const val BASE_DIR = ".MybatisGenerator"

        /**
         * 配置目录
         */
        private const val CONFIG_DIR = "config"

        /**
         * 日志文件目录
         */
        private const val LOG_DIR = "logs"

        /**
         * 数据库文件
         */
        private const val DATABASE_FILE = "configuration.db"

        fun initConfig() {
            try {
                // 获取Home目录
                val home = SystemUtil.userHome()
                val baseDir = home + FileUtil.SLASH + BASE_DIR
                // 创建配置目录
                if (FileUtil.mkdir(baseDir)) {
                    LOG.info("正在初始化配置信息...")
                    // 创建数据库配置
                    FileUtil.touch((baseDir + FileUtil.SLASH + CONFIG_DIR + FileUtil.SLASH +
                            DATABASE_FILE), DATABASE_FILE)
                    // 创建日志文件夹
                    FileUtil.mkdir(baseDir + FileUtil.SLASH + LOG_DIR)
                    LOG.info("完成初始化配置...")
                }
            } catch (e: Exception) {
                LOG.error("配置初始化失败: {}", e.message, e)
            }
        }
    }
}