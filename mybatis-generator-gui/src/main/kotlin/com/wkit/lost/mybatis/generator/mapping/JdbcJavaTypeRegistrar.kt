package com.wkit.lost.mybatis.generator.mapping

class JdbcJavaTypeRegistrar {

    companion object {
        private val JDBC_JAVA_TYPE_MAPPING_CACHE = HashMap<String, String>()
        private val IMPORT_JAVA_TYPE_CACHE = HashMap<String, String>()
        private val IMPORT_JAVA_TYPE_FLIP_CACHE = HashMap<String, String>()
        private val NOT_IMPORT_JAVA_TYPE_CACHE = HashSet<String>()

        init {
            IMPORT_JAVA_TYPE_CACHE["BigDecimal"] = "java.math.BigDecimal"
            IMPORT_JAVA_TYPE_CACHE["Date"] = "java.util.Date"
            IMPORT_JAVA_TYPE_CACHE["Timestamp"] = "java.sql.Timestamp"
            IMPORT_JAVA_TYPE_CACHE["Instant"] = "java.time.Instant"
            IMPORT_JAVA_TYPE_CACHE["LocalDate"] = "java.time.LocalDate"
            IMPORT_JAVA_TYPE_CACHE["LocalTime"] = "java.time.LocalTime"
            IMPORT_JAVA_TYPE_CACHE["LocalDateTime"] = "java.time.LocalDateTime"
            IMPORT_JAVA_TYPE_CACHE["ZonedDateTime"] = "java.time.ZonedDateTime"
            IMPORT_JAVA_TYPE_CACHE["OffsetTime"] = "java.time.OffsetTime"
            IMPORT_JAVA_TYPE_CACHE["OffsetDateTime"] = "java.time.OffsetDateTime"
            for ((k, v) in IMPORT_JAVA_TYPE_CACHE) {
                IMPORT_JAVA_TYPE_FLIP_CACHE[v] = k
            }
            NOT_IMPORT_JAVA_TYPE_CACHE.addAll(listOf("char", "Character", "boolean", "Boolean", "byte", "Byte",
                    "float", "Float", "double", "Double", "short", "Short", "int", "Integer", "long", "Long", "String"))
        }

        fun register(jdbcType: String, javaType: String) {
            JDBC_JAVA_TYPE_MAPPING_CACHE[jdbcType] = javaType
        }

        fun javaTypeString(jdbcType: String): String? {
            return takeIf {
                JDBC_JAVA_TYPE_MAPPING_CACHE.containsKey(jdbcType)
            }?.run {
                JDBC_JAVA_TYPE_MAPPING_CACHE[jdbcType]
            } ?: run {
                null
            }
        }

        fun javaTypeString(jdbcType: String, defaultType: String): String {
            return javaTypeString(jdbcType) ?: return defaultType
        }

        fun javaType(shortJavaType: String): String? {
            return takeIf {
                IMPORT_JAVA_TYPE_CACHE.containsKey(shortJavaType)
            }?.run {
                IMPORT_JAVA_TYPE_CACHE[shortJavaType]
            } ?: run {
                null
            }
        }

        fun javaType(shortJavaType: String, defaultType: String): String {
            return javaType(shortJavaType) ?: return defaultType
        }

        fun notNeedImport(javaType: String): Boolean {
            return NOT_IMPORT_JAVA_TYPE_CACHE.contains(javaType)
        }

        fun consistent(importJavaType: String, javaType: String): Boolean {
            val shortJavaType = IMPORT_JAVA_TYPE_FLIP_CACHE[importJavaType]
            return shortJavaType.takeIf {
                it.isNullOrBlank()
            }?.run {
                false
            } ?: run {
                shortJavaType == javaType
            }
        }
    }
}