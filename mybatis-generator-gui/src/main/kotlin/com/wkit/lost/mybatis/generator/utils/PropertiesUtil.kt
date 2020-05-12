package com.wkit.lost.mybatis.generator.utils

import org.apache.logging.log4j.LogManager
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.HashMap

class PropertiesUtil {

    companion object {

        private val LOG = LogManager.getLogger(PropertiesUtil)

        fun loadProperties(inputStream: InputStream?): MutableMap<String, String> {
            return inputStream?.run {
                return inputStream.use {
                    val map = HashMap<String, String>()
                    try {
                        val properties = Properties()
                        properties.load(InputStreamReader(inputStream, "utf-8"))
                        val keys = properties.stringPropertyNames()
                        keys.forEach {
                            map[it] = properties.getProperty(it, "")
                        }
                    } catch (e: Exception) {
                        LOG.error("Failed to read configuration file: {}", e.message, e)
                    }
                    map
                }
            } ?: run {
                HashMap<String, String>()
            }
        }
    }
}