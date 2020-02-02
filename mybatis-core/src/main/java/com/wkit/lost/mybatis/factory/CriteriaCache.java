package com.wkit.lost.mybatis.factory;

import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.CriteriaImpl;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.utils.ClassUtil;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link Criteria}实例缓存类
 * @author wvkity
 */
public class CriteriaCache implements Serializable {
    
    private static final long serialVersionUID = 4464948903920644603L;
    
    /**
     * 泛型类缓存
     */
    private volatile ConcurrentMap<Class<?>, Class<?>> actualClassCache = new ConcurrentHashMap<>( 128 );
    
    /**
     * {@link Criteria}实例缓存
     */
    private volatile ConcurrentMap<Class<?>, CriteriaImpl<?>> criteriaCache = new ConcurrentHashMap<>( 128 );
    
    /**
     * 构造方法
     */
    private CriteriaCache() {
    }
    
    /**
     * 实例化处理器
     */
    private static class CriteriaInstanceHolder {
        
        /**
         * 缓存实例对象
         */
        protected static final CriteriaCache INSTANCE = new CriteriaCache();
    }
    
    /**
     * 获取查询对象缓存实例
     * @return 实例
     */
    public static CriteriaCache getInstance() {
        return CriteriaInstanceHolder.INSTANCE;
    }
    
    /**
     * 获取查询对象实例
     * @param target 目标类
     * @param <T>    类型
     * @return 查询对象
     */
    public <T> CriteriaImpl<T> getCriteria( final Class<T> target ) {
        return getCriteria( target, null );
    }
    
    /**
     * 获取查询对象实例
     * @param target 目标类
     * @param alias  表别名
     * @param <T>    类型
     * @return 查询对象
     */
    @SuppressWarnings( "unchecked" )
    public <T> CriteriaImpl<T> getCriteria( final Class<T> target, final String alias ) {
        // 获取泛型类型
        Class<?> actualClass = actualClassCache.get( target );
        if ( actualClass == null ) {
            // 解析泛型
            actualClass = ClassUtil.getGenericType( target, 1 );
            if ( actualClass == Object.class ) {
                throw new MyBatisException( "Cannot get its corresponding generic type according to the specified class `" + target.getName() + "`" );
            }
            actualClassCache.putIfAbsent( target, actualClass );
            actualClass = actualClassCache.get( target );
        }
        // 获取实例
        CriteriaImpl<?> instance = criteriaCache.get( actualClass );
        if ( instance == null ) {
            criteriaCache.putIfAbsent( actualClass, CriteriaInstanceBuilder.build( actualClass ) );
            instance = criteriaCache.get( actualClass );
        }
        CriteriaImpl<?> newInstance = instance.deepClone();
        if ( StringUtil.hasText( alias ) ) {
            newInstance.useAlias( alias );
        }
        return (CriteriaImpl<T>) newInstance;
    }
}
