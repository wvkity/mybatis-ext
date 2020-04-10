package com.wkit.lost.mybatis.core.injector.execute;

import com.wkit.lost.mybatis.core.injector.method.AbstractGeneralQueryMethod;
import com.wkit.lost.mybatis.core.mapping.sql.query.CountProvider;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据指定对象查询记录方法映射
 * @author wvkity
 */
public class Count extends AbstractGeneralQueryMethod<CountProvider> {

    @Override
    public MappedStatement injectMappedStatement(TableWrapper table, Class<?> mapperInterface, Class<?> resultType) {
        return super.injectMappedStatement(table, mapperInterface, Long.class);
    }
}
