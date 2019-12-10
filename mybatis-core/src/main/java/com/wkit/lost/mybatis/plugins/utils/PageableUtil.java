package com.wkit.lost.mybatis.plugins.utils;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.paging.Pageable;
import com.wkit.lost.paging.Pager;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页工具类
 * @author wvkity
 */
public class PageableUtil {

    /**
     * 接口参数中是否存在{@code javax.servlet.ServletRequest}对象
     */
    private static boolean hasRequest;
    private static Class<?> requestClass;
    private static Method getParameterMap;
    private static final Map<String, String> PARAMETERS = new HashMap<>( 6, 1 );

    static {
        try {
            requestClass = Class.forName( "javax.servlet.ServletRequest" );
            getParameterMap = requestClass.getMethod( "getParameterMap" );
            hasRequest = true;
        } catch ( Exception e ) {
            hasRequest = false;
        }
        PARAMETERS.put( "page", "page" );
        PARAMETERS.put( "size", "size" );
        PARAMETERS.put( "orderBy", "orderBy" );
    }


    public static Pageable getPageable( Object parameter ) {
        if ( hasRequest && requestClass.isAssignableFrom( parameter.getClass() ) ) {
            try {
                MetaObject metaObject = SystemMetaObject.forObject( getParameterMap.invoke( parameter ) );
                Object pageValue = getParamValue( metaObject, "page" );
                Object sizeValue = getParamValue( metaObject, "size" );
                if ( pageValue != null ) {
                    return new Pager( String.valueOf( pageValue ), String.valueOf( sizeValue ) );
                }
            } catch ( Exception e ) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 获取参数值
     * @param metaObject 包装对象
     * @param paramName  参数名
     * @return 值
     */
    protected static Object getParamValue( MetaObject metaObject, String paramName ) {
        String supported = PARAMETERS.get( paramName );
        if ( supported != null && metaObject.hasGetter( supported ) ) {
            Object value = metaObject.getValue( supported );
            if ( ArrayUtil.isArray( value ) ) {
                Object[] values = ( Object[] ) value;
                if ( ArrayUtil.isEmpty( values ) ) {
                    return null;
                } else {
                    return values[ 0 ];
                }
            }
            return value;
        }
        return null;
    }
}
