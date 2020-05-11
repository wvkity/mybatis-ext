package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralQueryMethod;
import com.wvkity.mybatis.core.mapping.sql.query.CountCreator;
import com.wvkity.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据指定对象查询记录方法映射
 * @author wvkity
 */
public class Count extends AbstractGeneralQueryMethod<CountCreator> {

    @Override
    public MappedStatement injectMappedStatement(TableWrapper table, Class<?> mapperInterface, Class<?> resultType) {
        return super.injectMappedStatement(table, mapperInterface, Long.class);
    }
}
