package com.wkit.lost.mybatis.filling.proxy;

import com.wkit.lost.mybatis.filling.gen.AbstractGenerator;
import com.wkit.lost.mybatis.filling.gen.DateGenerator;
import com.wkit.lost.mybatis.filling.gen.GeneratedValue;
import com.wkit.lost.mybatis.filling.gen.InstantGenerator;
import com.wkit.lost.mybatis.filling.gen.LocalDateGenerator;
import com.wkit.lost.mybatis.filling.gen.LocalDateTimeGenerator;
import com.wkit.lost.mybatis.filling.gen.LocalTimeGenerator;
import com.wkit.lost.mybatis.filling.gen.OffsetDateTimeGenerator;
import com.wkit.lost.mybatis.filling.gen.OffsetTimeGenerator;
import com.wkit.lost.mybatis.filling.gen.SqlDateGenerator;
import com.wkit.lost.mybatis.filling.gen.TimestampGenerator;
import com.wkit.lost.mybatis.filling.gen.ZonedDateTimeGenerator;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 生成器工厂类
 * @author wvkity
 */
public class GeneratorFactory {

    /**
     * 日期类型生成器
     */
    private static final Map<Class<?>, Class<? extends AbstractGenerator>> DATE_TYPE_FILLING_CACHE = new ConcurrentHashMap<>( 10 );

    static {
        DATE_TYPE_FILLING_CACHE.put( Date.class, DateGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( java.sql.Date.class, SqlDateGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( Timestamp.class, TimestampGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( LocalTime.class, LocalTimeGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( LocalDate.class, LocalDateGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( LocalDateTime.class, LocalDateTimeGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( OffsetTime.class, OffsetTimeGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( OffsetDateTime.class, OffsetDateTimeGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( Instant.class, InstantGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( ZonedDateTime.class, ZonedDateTimeGenerator.class );
    }

    /**
     * 获取时间生成器类
     * @param clazz 类
     * @return 时间生成器
     */
    public static Class<? extends AbstractGenerator> getTimeGenerator( Class<?> clazz ) {
        return DATE_TYPE_FILLING_CACHE.get( clazz );
    }

    /**
     * 构建值
     * @param clazz 指定类型
     * @return 值
     */
    public static Object build( Class<? extends AbstractGenerator> clazz ) {
        return getProxy( clazz ).map( GeneratedValue::getValue ).orElse( null );
    }

    private static Optional<GeneratedValue> getProxy( Class<? extends AbstractGenerator> clazz ) {
        return Optional.ofNullable( instance( clazz ) ).map( value -> new GeneratorJdkProxy( value ).getProxy() );
    }

    private static <T> T instance( Class<T> clazz ) {
        if ( clazz != null ) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch ( Exception e ) {
                // ignore
            }
        }
        return null;
    }
}
