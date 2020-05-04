package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralQueryMethod;
import com.wvkity.mybatis.core.mapping.sql.query.ExistsProvider;
import com.wvkity.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 根据指定对象检查记录是否存在方法映射
 * @author wvkity
 */
public class Exists extends AbstractGeneralQueryMethod<ExistsProvider> {

    @Override
    public MappedStatement injectMappedStatement(TableWrapper table, Class<?> mapperInterface, Class<?> __) {
        return super.injectMappedStatement(table, mapperInterface, Integer.class);
    }
}
