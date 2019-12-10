package com.wkit.lost.mybatis.naming;

import com.wkit.lost.mybatis.annotation.naming.NamingStrategy;
import com.wkit.lost.mybatis.utils.CaseFormat;

import java.util.Locale;

/**
 * 命名策略工具
 * @author wvkity
 */
public class NamingStrategyUtil {

    /**
     * 命名转换
     * @param name     字符串
     * @param strategy 命名策略
     * @return 新的字符串
     */
    public static String valueOf( String name, NamingStrategy strategy ) {
        if ( strategy == null ) {
            strategy = NamingStrategy.CAMEL_HUMP_UPPERCASE;
        }
        switch ( strategy ) {
            case LOWERCASE:
                return name.toLowerCase( Locale.ENGLISH );
            case UPPERCASE:
                return name.toUpperCase( Locale.ENGLISH );
            case CAMEL_HUMP:
                return CaseFormat.LOWER_CAMEL.to( CaseFormat.LOWER_CAMEL_UNDERSCORE, name );
            case CAMEL_HUMP_LOWERCASE:
                return CaseFormat.LOWER_CAMEL.to( CaseFormat.LOWER_UNDERSCORE, name );
            case CAMEL_HUMP_UPPERCASE:
                return CaseFormat.LOWER_CAMEL.to( CaseFormat.UPPER_UNDERSCORE, name );
            case NORMAL:
            default:
                return name;
        }
    }
}
