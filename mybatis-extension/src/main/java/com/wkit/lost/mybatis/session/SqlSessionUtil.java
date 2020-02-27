package com.wkit.lost.mybatis.session;

import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionUtils;

import java.util.Optional;

import static org.mybatis.spring.SqlSessionUtils.getSqlSession;

public final class SqlSessionUtil {

    private SqlSessionUtil() {
    }

    /**
     * 获取{@link SqlSessionFactory}
     * @param clazz 实体类
     * @return {@link SqlSessionFactory}
     */
    public static SqlSessionFactory getSqlSessionFactory( Class<?> clazz ) {
        return Optional.ofNullable( TableHandler.getConfiguration( clazz ) )
                .map( SqlSessionUtil::getSqlSessionFactory ).orElse( null );
    }

    /**
     * 获取{@link SqlSessionFactory}
     * @param configuration {@link Configuration}
     * @return {@link SqlSessionFactory}
     */
    public static SqlSessionFactory getSqlSessionFactory( Configuration configuration ) {
        return Optional.ofNullable( MyBatisConfigCache.getCustomConfiguration( configuration ) )
                .map( MyBatisCustomConfiguration::getSqlSessionFactory ).orElse( null );
    }

    /**
     * 获取批量操作{@link SqlSession}
     * @param clazz 实体类
     * @return {@link SqlSession}
     */
    public static SqlSession batchSession( Class<?> clazz ) {
        return openSession( clazz, ExecutorType.BATCH );
    }

    /**
     * 获取{@link SqlSession}
     * @param clazz        实体类
     * @param executorType 执行类行
     * @return {@link SqlSession}
     */
    public static SqlSession openSession( Class<?> clazz, ExecutorType executorType ) {
        return Optional.ofNullable( TableHandler.getConfiguration( clazz ) )
                .map( it -> openSession( it, executorType ) ).orElse( null );
    }

    /**
     * 获取{@link SqlSession}
     * @param configuration {@link Configuration}
     * @param executorType  执行类行
     * @return {@link SqlSession}
     */
    public static SqlSession openSession( Configuration configuration, ExecutorType executorType ) {
        if ( configuration != null ) {
            if ( executorType == null ) {
                executorType = ExecutorType.SIMPLE;
            }
            try {
                SqlSessionFactory factory = getSqlSessionFactory( configuration );
                // 创建session
                return getSqlSession( factory, executorType, null );
            } catch ( Exception e ) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 释放{@link SqlSession}
     * @param session {@link SqlSession}
     * @param clazz   实体类
     */
    public static void closeSession( SqlSession session, Class<?> clazz ) {
        closeSession( session, getSqlSessionFactory( clazz ) );
    }

    /**
     * 释放{@link SqlSession}
     * @param session        {@link SqlSession}
     * @param sessionFactory {@link SqlSessionFactory}
     */
    public static void closeSession( SqlSession session, SqlSessionFactory sessionFactory ) {
        SqlSessionUtils.closeSqlSession( session, sessionFactory );
    }
}
