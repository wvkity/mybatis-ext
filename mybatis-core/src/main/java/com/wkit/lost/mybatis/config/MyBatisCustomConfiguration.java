package com.wkit.lost.mybatis.config;

import com.wkit.lost.mybatis.annotation.extension.Dialect;
import com.wkit.lost.mybatis.annotation.naming.NamingStrategy;
import com.wkit.lost.mybatis.filling.MetaObjectFillingHandler;
import com.wkit.lost.mybatis.keygen.KeyGenerator;
import com.wkit.lost.mybatis.naming.DefaultPhysicalNamingStrategy;
import com.wkit.lost.mybatis.naming.PhysicalNamingStrategy;
import com.wkit.lost.mybatis.resolver.EntityResolver;
import com.wkit.lost.mybatis.resolver.FieldResolver;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.sql.injector.SqlInjector;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.Serializable;

/**
 * MyBatis自定义配置
 * @author DT
 */
@Data
@Accessors( chain = true )
@NoArgsConstructor
public class MyBatisCustomConfiguration implements Serializable {

    private static final long serialVersionUID = -3928045766093460184L;

    /**
     * 数据库类型
     */
    private Dialect dialect;

    /**
     * 当前{@link org.apache.ibatis.session.Configuration}的{@link SqlSessionFactory}
     */
    private SqlSessionFactory sqlSessionFactory;

    /**
     * SqlSession对象
     */
    private SqlSession sqlSession;

    /**
     * 实体解析器
     */
    private EntityResolver entityResolver;

    /**
     * 属性解析器
     */
    private FieldResolver fieldResolver;

    /**
     * SQL注入器
     */
    private SqlInjector injector;

    /**
     * 命名策略
     */
    private NamingStrategy strategy;

    /**
     * 命名处理
     */
    private PhysicalNamingStrategy physicalNamingStrategy = new DefaultPhysicalNamingStrategy();

    /**
     * 主键生成器执行类型
     */
    private boolean before;

    /**
     * 主键是否为UUID
     */
    private boolean uuid;

    /**
     * 主键是否自增
     */
    private boolean identity;

    /**
     * 是否为雪花算法主键
     */
    private boolean worker;

    /**
     * 是否为雪花算法字符串主键
     */
    private boolean workerString;

    /**
     * 表名前缀
     */
    private String tablePrefix;

    /**
     * 数据库catalog
     */
    private String catalog;

    /**
     * 数据库schema
     */
    private String schema;

    /**
     * 自动识别主键(缺省@Id注解时)
     */
    private boolean autoDiscernPrimaryKey = true;

    /**
     * 全局主键标识(用于自动识别主键)
     */
    private String[] primaryKeys = { "id" };

    /**
     * 主键接口
     */
    private KeyGenerator keyGenerator;

    /**
     * 使用简单类型
     */
    private boolean useSimpleType = true;

    /**
     * 是否使用Java类型
     */
    private boolean useJavaType;

    /**
     * JDBC类型自动映射(缺省jdbcType)
     */
    private boolean jdbcTypeAutoMapping;

    /**
     * 枚举类型学转简单类型
     */
    private boolean enumAsSimpleType;

    /**
     * 关键字
     */
    private String wrapKeyWord = "";

    /**
     * 非空判断
     */
    private boolean checkNotEmpty = true;

    /**
     * 是否启用方法上的注解
     */
    private boolean enableMethodAnnotation = false;

    /**
     * 是否使用默认分页插件
     */
    private boolean usePageablePlugin = true;

    /**
     * 是否使用默认的limit查询插件
     */
    private boolean useLimitPlugin = true;

    /**
     * 标识已删除
     */
    private String logicDeleted = "1";

    /**
     * 标识未删除
     */
    private String logicNotDeleted = "0";

    /**
     * 主键序列(雪花算法)
     */
    private Sequence sequence;

    /**
     * 自动填充值处理器
     */
    private MetaObjectFillingHandler metaObjectFillingHandler;

    /**
     * 缓存当前对象
     * @param factory {@link SqlSessionFactory}
     * @return {@link SqlSessionFactory}
     */
    public SqlSessionFactory cacheSelf( SqlSessionFactory factory ) {
        if ( factory != null ) {
            cacheSelf( factory.getConfiguration() );
        }
        return factory;
    }

    /**
     * 缓存当前对象
     * @param configuration {@link Configuration}
     */
    public void cacheSelf( Configuration configuration ) {
        if ( configuration != null ) {
            MyBatisConfigCache.cacheCustomConfiguration( configuration, this );
        }
    }
}
