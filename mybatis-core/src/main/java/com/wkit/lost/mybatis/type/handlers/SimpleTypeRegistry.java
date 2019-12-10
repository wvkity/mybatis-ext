package com.wkit.lost.mybatis.type.handlers;

import com.wkit.lost.mybatis.utils.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 简单类型注册器
 * @author wvkity
 */
public class SimpleTypeRegistry {

    /**
     * 简单类型集合
     */
    private static final Set<Class<?>> SIMPLE_TYPE_SET = new HashSet<>();

    /**
     * JDK8+新时间API
     */
    private static final String[] JAVA8_DATE_TYPE = {
            "java.time.Instant",
            "java.time.LocalDateTime",
            "java.time.LocalDate",
            "java.time.LocalTime",
            "java.time.OffsetDateTime",
            "java.time.OffsetTime",
            "java.time.ZonedDateTime",
            "java.time.Year",
            "java.time.Month",
            "java.time.YearMonth"
    };

    static {
        SIMPLE_TYPE_SET.add( String.class );
        SIMPLE_TYPE_SET.add( Byte.class );
        SIMPLE_TYPE_SET.add( Short.class );
        SIMPLE_TYPE_SET.add( Character.class );
        SIMPLE_TYPE_SET.add( Integer.class );
        SIMPLE_TYPE_SET.add( Long.class );
        SIMPLE_TYPE_SET.add( Float.class );
        SIMPLE_TYPE_SET.add( Double.class );
        SIMPLE_TYPE_SET.add( Boolean.class );
        SIMPLE_TYPE_SET.add( Date.class );
        SIMPLE_TYPE_SET.add( Class.class );
        SIMPLE_TYPE_SET.add( BigInteger.class );
        SIMPLE_TYPE_SET.add( BigDecimal.class );
        Arrays.stream( JAVA8_DATE_TYPE ).forEach( SimpleTypeRegistry::registerSimpleTypeSilence );
    }

    /**
     * 注册简单类型
     * @param clazz 类型
     */
    public static void registerSimpleType( Class<?> clazz ) {
        if ( clazz != null ) {
            SIMPLE_TYPE_SET.add( clazz );
        }
    }

    /**
     * 注册多个简单类型
     * <p>多个类型请使用英文逗号隔开</p>
     * @param classes 类型
     */
    public static void registerSimpleType( String classes ) {
        if ( !StringUtil.isBlank( classes ) ) {
            Arrays.stream( classes.split( "," ) ).forEach( SimpleTypeRegistry::registerSimpleTypeSilence );
        }
    }

    /**
     * 注册简单类型
     * @param className 简单类名
     */
    public static void registerSimpleTypeSilence( String className ) {
        try {
            registerSimpleType( Class.forName( className ) );
        } catch ( ClassNotFoundException e ) {
            // ignore
        }
    }

    /**
     * 检查是否为简单类型
     * @param clazz 待检查类
     * @return true | false
     */
    public static boolean isSimpleType( Class<?> clazz ) {
        return SIMPLE_TYPE_SET.contains( clazz );
    }
}
