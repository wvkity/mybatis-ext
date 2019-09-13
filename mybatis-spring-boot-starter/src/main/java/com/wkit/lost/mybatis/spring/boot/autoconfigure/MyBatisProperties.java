package com.wkit.lost.mybatis.spring.boot.autoconfigure;

import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.session.MyBatisConfiguration;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * mybatis配置
 * @author DT
 */
@Data
@Accessors( chain = true )
@ConfigurationProperties( prefix = "lost.mybatis" )
public class MyBatisProperties {

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    /**
     * MyBatis配置文件位置
     */
    private String configLocation;

    /**
     * MyBatis接口映射文件位置
     */
    private String[] mapperLocations = new String[]{ "classpath*:/mybatis/mapper/**/*.xml" };

    /**
     * 类型别名包(分隔符: [,|;|\t|\n])
     */
    private String typeAliasesPackage;

    /**
     * 用于过滤类型别名的超类
     */
    private Class<?> typeAliasesSuperType;

    /**
     * 类型处理包
     */
    private String typeHandlersPackage;

    /**
     * 枚举类型包
     */
    private String typeEnumsPackage;

    /**
     * 是否对MyBatis XML配置文件执行状态检查
     */
    private boolean checkConfigLocation = false;

    /**
     * {@link org.mybatis.spring.SqlSessionTemplate}执行模式
     */
    private ExecutorType executorType;

    /**
     * 额外属性配置
     */
    private Properties configurationProperties;

    /**
     * 配置对象
     */
    @NestedConfigurationProperty
    private MyBatisConfiguration configuration;

    /**
     * MyBatis自定义全局配置
     */
    @NestedConfigurationProperty
    private MyBatisCustomConfiguration customConfiguration;

    public Resource[] resolveMapperLocations() {
        return Stream.of( Optional.ofNullable( this.mapperLocations ).orElse( new String[ 0 ] ) )
                .flatMap( location -> Stream.of( getResources( location ) ) )
                .toArray( Resource[]::new );
    }

    private Resource[] getResources( String location ) {
        try {
            return resourceResolver.getResources( location );
        } catch ( IOException e ) {
            return new Resource[ 0 ];
        }
    }
}
