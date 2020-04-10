package com.wkit.lost.mybatis.type.registry;

import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseDate;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JDBC类型映射注册器
 * @author wvkity
 */
public abstract class JdbcTypeMappingRegister {

    private static final Map<Class<?>, JdbcType> MAPPING_CACHE = new ConcurrentHashMap<>(32);

    static {
        // NUMBER
        MAPPING_CACHE.put(long.class, JdbcType.BIGINT);
        MAPPING_CACHE.put(Long.class, JdbcType.BIGINT);
        MAPPING_CACHE.put(int.class, JdbcType.INTEGER);
        MAPPING_CACHE.put(Integer.class, JdbcType.INTEGER);
        MAPPING_CACHE.put(short.class, JdbcType.SMALLINT);
        MAPPING_CACHE.put(Short.class, JdbcType.SMALLINT);
        MAPPING_CACHE.put(float.class, JdbcType.FLOAT);
        MAPPING_CACHE.put(Float.class, JdbcType.FLOAT);
        MAPPING_CACHE.put(double.class, JdbcType.DOUBLE);
        MAPPING_CACHE.put(Double.class, JdbcType.DOUBLE);
        MAPPING_CACHE.put(BigDecimal.class, JdbcType.NUMERIC);
        // VARCHAR
        MAPPING_CACHE.put(String.class, JdbcType.VARCHAR);
        MAPPING_CACHE.put(Locale.class, JdbcType.VARCHAR);
        MAPPING_CACHE.put(Currency.class, JdbcType.VARCHAR);
        MAPPING_CACHE.put(TimeZone.class, JdbcType.VARCHAR);
        MAPPING_CACHE.put(Class.class, JdbcType.VARCHAR);
        MAPPING_CACHE.put(char.class, JdbcType.CHAR);
        MAPPING_CACHE.put(Character.class, JdbcType.CHAR);
        MAPPING_CACHE.put(Clob.class, JdbcType.CLOB);
        MAPPING_CACHE.put(Blob.class, JdbcType.BLOB);
        MAPPING_CACHE.put(byte[].class, JdbcType.BLOB);
        MAPPING_CACHE.put(Byte[].class, JdbcType.BLOB);
        // TIME
        MAPPING_CACHE.put(Date.class, JdbcType.TIMESTAMP);
        MAPPING_CACHE.put(java.sql.Date.class, JdbcType.TIMESTAMP);
        MAPPING_CACHE.put(Calendar.class, JdbcType.TIMESTAMP);
        MAPPING_CACHE.put(Timestamp.class, JdbcType.TIMESTAMP);
        // JSR-310 TIME API
        MAPPING_CACHE.put(Instant.class, JdbcType.TIMESTAMP);
        MAPPING_CACHE.put(LocalDateTime.class, JdbcType.TIMESTAMP);
        MAPPING_CACHE.put(OffsetDateTime.class, JdbcType.TIMESTAMP);
        MAPPING_CACHE.put(ZonedDateTime.class, JdbcType.TIMESTAMP);
        MAPPING_CACHE.put(JapaneseDate.class, JdbcType.DATE);
        MAPPING_CACHE.put(LocalDate.class, JdbcType.DATE);
        MAPPING_CACHE.put(OffsetTime.class, JdbcType.TIME);
    }

    /**
     * 注册JDBC类型映射
     * @param clazz    类
     * @param jdbcType JDBC类型
     */
    public static void register(Class<?> clazz, JdbcType jdbcType) {
        if (clazz != null && jdbcType != null) {
            MAPPING_CACHE.put(clazz, jdbcType);
        }
    }

    private static JdbcType getOrNull(final Class<?> clazz) {
        return MAPPING_CACHE.getOrDefault(clazz, null);
    }

    /**
     * 获取JDBC类型
     * @param clazz 类
     * @return {@link JdbcType}
     */
    public static JdbcType getJdbcType(Class<?> clazz) {
        return Optional.ofNullable(clazz).map(JdbcTypeMappingRegister::getOrNull).orElse(null);
    }

}
