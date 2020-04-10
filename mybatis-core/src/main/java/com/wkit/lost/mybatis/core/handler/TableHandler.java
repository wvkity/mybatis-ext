package com.wkit.lost.mybatis.core.handler;

import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableBuilder;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.parser.DefaultEntityParser;
import com.wkit.lost.mybatis.core.parser.EntityParser;
import com.wkit.lost.mybatis.utils.ClassUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库表处理器
 */
@Log4j2
public final class TableHandler {

    private TableHandler() {
    }

    /**
     * 表映射信息缓存
     */
    private static final Map<Class<?>, TableWrapper> TABLE_WRAPPER_CACHE = new ConcurrentHashMap<>(64);

    /**
     * 配置缓存
     */
    private static final Map<Class<?>, Configuration> CONFIGURATION_CACHE = new ConcurrentHashMap<>(64);

    /**
     * 默认实体类解析器
     */
    private static final EntityParser ENTITY_PARSER = new DefaultEntityParser();

    /**
     * 拦截解析实体类
     * @param assistant 映射构建辅助对象
     * @param entity    实体类
     * @return 表映射对象
     */
    public synchronized static TableWrapper parse(final MapperBuilderAssistant assistant, final Class<?> entity) {
        if (entity != null) {
            TableWrapper wrapper = TABLE_WRAPPER_CACHE.getOrDefault(entity, null);
            if (wrapper == null) {
                log.debug("Resolve entity class - table mapping information：`{}`", entity.getCanonicalName());
                // 初始化构建器
                Configuration configuration = assistant.getConfiguration();
                // 自定义配置
                MyBatisCustomConfiguration customConfiguration =
                        MyBatisConfigCache.getCustomConfiguration(configuration);
                // 实体解析器
                EntityParser parser = Optional.ofNullable(customConfiguration.getEntityParser())
                        .orElse(TableHandler.ENTITY_PARSER);
                TableBuilder builder = TableBuilder.create()
                        .entity(entity)
                        .namespace(assistant.getCurrentNamespace())
                        .configuration(customConfiguration);
                // 解析
                TableWrapper tableWrapper = parser.parse(builder);
                if (!TABLE_WRAPPER_CACHE.containsKey(entity)) {
                    TABLE_WRAPPER_CACHE.putIfAbsent(entity, tableWrapper);
                    CONFIGURATION_CACHE.putIfAbsent(entity, configuration);
                }
                return TABLE_WRAPPER_CACHE.get(entity);
            }
            return wrapper;
        }
        return null;
    }

    /**
     * 根据实体类获取数据库表映射对象
     * @param entity 实体类
     * @return 数据库表映射对象
     */
    public static TableWrapper getTable(final Class<?> entity) {
        return entity == null ? null : Optional.ofNullable(TABLE_WRAPPER_CACHE.getOrDefault(entity, null))
                .orElse(TABLE_WRAPPER_CACHE.getOrDefault(ClassUtil.getRealClass(entity), null));
    }

    /**
     * 根据实体类获取{@link Configuration}对象
     * @param entity 实体类
     * @return {@link Configuration}对象
     */
    public static Configuration getConfiguration(final Class<?> entity) {
        return Optional.ofNullable(CONFIGURATION_CACHE.getOrDefault(entity, null)).orElse(null);
    }

    /**
     * 根据数据库表映射对象获取{@link Configuration}对象
     * @param table 实体-表映射对象
     * @return {@link Configuration}对象
     */
    public static Configuration getConfiguration(final TableWrapper table) {
        return Optional.ofNullable(table)
                .map(it -> CONFIGURATION_CACHE.getOrDefault(it.getEntity(), null))
                .orElse(null);
    }

    /**
     * 获取主键
     * @param klass 实体类
     * @return 主键字段包装对象
     */
    public static ColumnWrapper getPrimaryKey(final Class<?> klass) {
        return Optional.ofNullable(klass)
                .flatMap(it -> Optional.ofNullable(TableHandler.getTable(it)).map(TableWrapper::getPrimaryKey))
                .orElse(null);
    }
}
