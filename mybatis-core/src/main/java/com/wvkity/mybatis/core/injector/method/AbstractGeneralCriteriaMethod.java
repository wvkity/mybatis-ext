package com.wvkity.mybatis.core.injector.method;

import com.wvkity.mybatis.core.mapping.script.ScriptBuilder;
import com.wvkity.mybatis.core.mapping.script.ScriptBuilderFactory;
import com.wvkity.mybatis.core.mapping.sql.Creator;
import com.wvkity.mybatis.core.metadata.TableWrapper;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * Criteria相关方法注入器
 * @param <T> SQL提供类
 * @author wvkity
 * @see Criteria
 */
public abstract class AbstractGeneralCriteriaMethod<T extends Creator> extends AbstractGeneralMethod<T> {

    @Override
    public MappedStatement injectMappedStatement(TableWrapper table, Class<?> mapperInterface, Class<?> resultType) {
        Class<?> entity = table.getEntity();
        ScriptBuilder builder = ScriptBuilderFactory.create(target(), table, entity, null);
        Class<?> returnType = getResultType();
        return addSelectMappedStatement(mapperInterface, applyMethod(), createSqlSource(builder, entity), null,
                returnType == null ? resultType : returnType, table);
    }

    /**
     * 获取返回值类型
     * @return 返回值类型
     */
    public Class<?> getResultType() {
        return null;
    }

}
