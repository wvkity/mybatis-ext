package com.wvkity.mybatis.core.injector;

import com.wvkity.mybatis.config.MyBatisConfigCache;
import com.wvkity.mybatis.core.handler.TableHandler;
import com.wvkity.mybatis.core.injector.method.Method;
import com.wvkity.mybatis.core.metadata.TableWrapper;
import com.wvkity.mybatis.mapper.SimpleMapper;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.builder.MapperBuilderAssistant;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Set;

/**
 * 抽象SQL注入器
 * @author wvkity
 */
@Log4j2
public abstract class AbstractInjector implements Injector {

    @Override
    public void inject(MapperBuilderAssistant assistant, Class<?> mapperInterface) {
        Type[] genericTypes = getGenericTypes(mapperInterface);
        Class<?> entityClass = getGenericType(genericTypes, 0);
        if (entityClass != null) {
            String interfaceName = mapperInterface.toString();
            Set<String> mapperRegistryCache = MyBatisConfigCache.getMapperInterfaceCache(assistant.getConfiguration());
            if (!mapperRegistryCache.contains(interfaceName)) {
                Collection<Method> methods = getMethodsForInject(mapperInterface);
                if (CollectionUtil.hasElement(methods)) {
                    // 拦截解析实体-表映射
                    TableWrapper table = TableHandler.parse(assistant, entityClass);
                    // 获取返回值类型
                    int index = SimpleMapper.class.isAssignableFrom(mapperInterface) ? 0 : 1;
                    Class<?> resultType = index == 0 ? entityClass : getGenericType(genericTypes, index);
                    // 注入方法
                    methods.forEach(it -> it.inject(assistant, mapperInterface, resultType, table));
                } else {
                    log.warn("No effective injection method was found");
                }
                mapperRegistryCache.add(interfaceName);
            }
        }
    }

    /**
     * 获取泛型实体类
     * @param genericTypes 泛型数组
     * @param index        索引
     * @return 泛型实体类
     */
    public Class<?> getGenericType(Type[] genericTypes, int index) {
        if (ArrayUtil.isEmpty(genericTypes) || index < 0 || index > genericTypes.length) {
            return null;
        }
        Type target = genericTypes[index];
        return target == null ? null : (Class<?>) target;
    }

    /**
     * 获取接口泛型
     * @param mapperInterface Mapper接口
     * @return 泛型数组
     */
    public Type[] getGenericTypes(final Class<?> mapperInterface) {
        Type[] interfaces = mapperInterface.getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
                for (Type it : genericTypes) {
                    if (!(it instanceof TypeVariable) && !(it instanceof WildcardType)) {
                        return genericTypes;
                    }
                    break;
                }
                break;
            }
        }
        return null;
    }

    /**
     * 获取注入方法
     * @param mapperInterface Mapper接口
     * @return 方法列表
     */
    public abstract Collection<Method> getMethodsForInject(final Class<?> mapperInterface);
}
