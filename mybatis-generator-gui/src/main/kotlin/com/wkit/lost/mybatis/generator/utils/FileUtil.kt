package com.wkit.lost.mybatis.generator.utils

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

/**
 * 文件工具
 * @author wvkity
 */
class FileUtil {

    companion object {

        private val LOG: Logger = LogManager.getLogger(FileUtil)

        /**
         * 斜杠
         */
        val SLASH: String = File.separator

        fun isDir(filePath: String): Boolean {
            return File(filePath).takeIf {
                it.exists()
            }?.run {
                this.isDirectory
            } ?: run {
                false
            }
        }

        fun exists(filePath: String?): Boolean {
            return filePath.takeIf {
                it.isNullOrBlank()
            }?.run {
                false
            } ?: run {
                File(filePath!!).exists()
            }
        }

        /**
         * 创建文件夹
         * @param dir 文件夹路径
         * @return true: 成功 false: 失败
         */
        fun mkdir(dir: String?): Boolean {
            dir?.run {
                return mkdir(File(dir))
            }
            return false
        }

        /**
         * 创建文件夹
         * @param dir 文件夹
         * @return true: 成功 false: 失败
         */
        fun mkdir(dir: File): Boolean {
            try {
                // 如果是文件
                if (dir.isFile) {
                    val parentFile = dir.parentFile
                    if (!parentFile.exists()) {
                        return parentFile.mkdirs()
                    }
                } else {
                    if (!dir.exists()) {
                        return dir.mkdirs()
                    }
                }
                return true
            } catch (e: Exception) {
                LOG.error("`{}`文件夹创建失败: {}", dir.absolutePath, e.message, e)
            }
            return false
        }

        /**
         * 创建文件
         * (文件不存在则从classpath路径下复制指定的文件)
         * @param file 目标文件
         * @param source 源文件
         * @return true: 成功 false: 失败
         */
        fun touch(file: String?, source: String?): Boolean {
            file?.run {
                return touch(File(file), source)
            }
            return false
        }

        /**
         * 创建文件
         * (文件不存在则从classpath路径下复制指定的文件)
         * @param file 目标文件
         * @param source 源文件
         * @return true: 成功 false: 失败
         */
        fun touch(file: File, source: String?): Boolean {
            try {
                if (mkdir(file.parent)) {
                    // 检查文件是否存在
                    if (!file.exists()) {
                        // 资源文件是否存在
                        source?.run {
                            var read = -1
                            val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(source)
                            val outputStream = FileOutputStream(file)
                            inputStream.use { input ->
                                outputStream.use {
                                    while (input.read().also { read = it } != -1) {
                                        it.write(read)
                                    }
                                }
                            }
                        }
                        return true
                    }
                }
            } catch (e: Exception) {
                LOG.error("文件创建失败: {}", e.message, e)
            }
            return false
        }
    }
}