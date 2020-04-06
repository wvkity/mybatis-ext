package com.wkit.lost.mybatis.core.data.auditing.time.proxy;

import com.wkit.lost.mybatis.core.data.auditing.time.provider.AbstractProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.DateProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.DateTimeProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.InstantProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.LocalDateProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.LocalDateTimeProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.LocalTimeProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.OffsetDateTimeProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.OffsetTimeProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.TimestampProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.provider.ZonedDateTimeProvider;

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

public class DateTimeProviderFactory {

    /**
     * 日期提供者
     */
    private static final Map<Class<?>, Class<? extends AbstractProvider>> DATE_TIME_PROVIDER_CACHE = new ConcurrentHashMap<>();

    static {
        DATE_TIME_PROVIDER_CACHE.put( Date.class, DateProvider.class );
        DATE_TIME_PROVIDER_CACHE.put( Timestamp.class, TimestampProvider.class );
        DATE_TIME_PROVIDER_CACHE.put( LocalDate.class, LocalDateProvider.class );
        DATE_TIME_PROVIDER_CACHE.put( LocalTime.class, LocalTimeProvider.class );
        DATE_TIME_PROVIDER_CACHE.put( LocalDateTime.class, LocalDateTimeProvider.class );
        DATE_TIME_PROVIDER_CACHE.put( OffsetTime.class, OffsetTimeProvider.class );
        DATE_TIME_PROVIDER_CACHE.put( OffsetDateTime.class, OffsetDateTimeProvider.class );
        DATE_TIME_PROVIDER_CACHE.put( Instant.class, InstantProvider.class );
        DATE_TIME_PROVIDER_CACHE.put( ZonedDateTime.class, ZonedDateTimeProvider.class );
    }

    /**
     * 时间提供者构建器
     * @author wvkity
     */
    public interface Builder {

        /**
         * 构建时间提供者
         * @return 时间提供者
         */
        DateTimeProvider build();
    }

    /**
     * 基于动态代理时间提供者构建器
     * @author wvkity
     */
    public static final class ProviderBuilder implements Builder {

        private Class<?> target;

        public static ProviderBuilder create() {
            return new ProviderBuilder();
        }

        public ProviderBuilder target( Class<?> target ) {
            this.target = target;
            return this;
        }

        @Override
        public DateTimeProvider build() {
            return Optional.ofNullable( getProviderClass( this.target ) )
                    .flatMap( it -> Optional.ofNullable( newInstance( it ) )
                            .map( instance -> new DateTimeProviderProxy( instance ).getTarget() ) )
                    .orElse( null );
        }

        private <T> T newInstance( Class<T> clazz ) {
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

    /**
     * 注册时间提供者
     * @param target        目标类型
     * @param providerClass 提供者类型
     */
    public static void register( Class<?> target, Class<? extends AbstractProvider> providerClass ) {
        if ( target != null && providerClass != null ) {
            DATE_TIME_PROVIDER_CACHE.put( target, providerClass );
        }
    }

    public static Class<? extends AbstractProvider> getProviderClass( Class<?> target ) {
        return DATE_TIME_PROVIDER_CACHE.getOrDefault( target, null );
    }
}
