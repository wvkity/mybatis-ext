package com.wkit.lost.mybatis.plugins.utils;

import com.wkit.lost.mybatis.plugins.exception.MyBatisPluginException;
import org.apache.ibatis.mapping.BoundSql;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 执行工具类
 * @author DT
 */
public final class Executors {

    private static Field additionalParametersField;

    static {
        try {
            additionalParametersField = BoundSql.class.getDeclaredField( "additionalParameters" );
            additionalParametersField.setAccessible( true );
        } catch ( Exception e ) {
            throw new MyBatisPluginException( "Failure to obtain BoundSql attribute additionalParameters：" + e, e );
        }
    }

    /**
     * 获取{@link BoundSql}对象的additionalParameters属性值
     * @param boundSql {@link BoundSql}对象
     * @return 属性值
     */
    @SuppressWarnings( "unchecked" )
    public static Map<String, Object> getAdditionalParameter( final BoundSql boundSql ) {
        try {
            return ( Map<String, Object> ) additionalParametersField.get( boundSql );
        } catch ( Exception e ) {
            throw new MyBatisPluginException( "Failure to obtain BoundSql attribute value additionalParameters: " + e, e );
        }
    }
}
