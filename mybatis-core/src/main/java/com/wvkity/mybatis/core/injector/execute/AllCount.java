package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralQueryMethod;
import com.wvkity.mybatis.core.mapping.sql.query.AllCountCreator;
import com.wvkity.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 查询所有记录数方法映射
 * @author wvkity
 */
public class AllCount extends AbstractGeneralQueryMethod<AllCountCreator> {

    @Override
    public MappedStatement injectMappedStatement(TableWrapper table, Class<?> mapperInterface, Class<?> resultType) {
        return super.injectMappedStatement(table, mapperInterface, Long.class);
    }
}
