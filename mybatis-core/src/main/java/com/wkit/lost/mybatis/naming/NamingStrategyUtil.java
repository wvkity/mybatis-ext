package com.wkit.lost.mybatis.naming;

import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.annotation.naming.NamingStrategy;

import java.util.Locale;

/**
 * 命名策略工具
 * @author DT
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
                return name.toLowerCase( Locale.ROOT );
            case UPPERCASE:
                return name.toUpperCase( Locale.ROOT );
            case CAMEL_HUMP:
                return StringUtil.camelHumpToUnderline( name );
            case CAMEL_HUMP_LOWERCASE:
                return StringUtil.camelHumpToUnderline( name ).toLowerCase( Locale.ROOT );
            case CAMEL_HUMP_UPPERCASE:
                return StringUtil.camelHumpToUnderline( name ).toUpperCase( Locale.ROOT );
            case NORMAL:
            default:
                return name;
        }
    }
}
