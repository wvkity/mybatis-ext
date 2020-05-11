package com.wvkity.mybatis.core.injector.method;

import com.wvkity.mybatis.core.mapping.sql.Creator;
import com.wvkity.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 抽象插入方法映射注入器
 * @author wvkity
 */
public abstract class AbstractGeneralInsertMethod<T extends Creator> extends AbstractGeneralMethod<T> {

    @Override
    public MappedStatement injectMappedStatement(TableWrapper table, Class<?> mapperInterface, Class<?> resultType) {
        return addInsertMappedStatement(mapperInterface, resultType, table, target());
    }
}
