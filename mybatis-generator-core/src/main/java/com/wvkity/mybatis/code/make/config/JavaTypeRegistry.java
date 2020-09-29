package com.wvkity.mybatis.code.make.config;

import com.wvkity.mybatis.code.make.utils.StringUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class JavaTypeRegistry {

    private JavaTypeRegistry() {
    }

    private static final Map<String, String> JDBC_TYPE_MAPPING_CACHE = new HashMap<>();
    private static final Map<String, String> IMPORT_JAVA_TYPE_SHORT_CACHE = new HashMap<>();
    private static final Map<String, String> IMPORT_JAVA_TYPE_FLIP_CACHE = new HashMap<>();
    private static final Set<String> NOT_IMPORT_JAVA_TYPE_CACHE = new HashSet<>();

    static {
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("BigDecimal", "java.math.BigDecimal");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("Date", "java.util.Date");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("Timestamp", "java.sql.Timestamp");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("Instant", "java.time.Instant");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("LocalDate", "java.time.LocalDate");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("LocalTime", "java.time.LocalTime");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("LocalDateTime", "java.time.LocalDateTime");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("ZonedDateTime", "java.time.ZonedDateTime");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("OffsetTime", "java.time.OffsetTime");
        IMPORT_JAVA_TYPE_SHORT_CACHE.put("OffsetDateTime", "java.time.OffsetDateTime");
        IMPORT_JAVA_TYPE_SHORT_CACHE.forEach((k, v) -> IMPORT_JAVA_TYPE_FLIP_CACHE.put(v, k));
        NOT_IMPORT_JAVA_TYPE_CACHE.addAll(Arrays.asList("char", "Character", "boolean", "Boolean", "byte", "Byte",
                "float", "Float", "double", "Double", "short", "Short", "int", "Integer", "long", "Long", "String"));
    }

    /**
     * 注册JDBC类型映射
     * @param jdbcType JDBC类型
     * @param javaType JAVA类型
     */
    public static void register(final String jdbcType, final String javaType) {
        if (StringUtil.isNotEmpty(jdbcType) && StringUtil.isNotEmpty(javaType)) {
            JDBC_TYPE_MAPPING_CACHE.put(jdbcType, javaType);
        }
    }
    
    public static String javaType(final String shortJavaType, final String defaultValue) {
        return Optional.ofNullable(javaType(shortJavaType)).filter(StringUtil::isNotEmpty).orElse(defaultValue);
    }
    
    public static String javaType(final String shortJavaType) {
        return Optional.ofNullable(shortJavaType).filter(StringUtil::isNotEmpty)
                .map(IMPORT_JAVA_TYPE_SHORT_CACHE::get).orElse(null);
    }
    
}
