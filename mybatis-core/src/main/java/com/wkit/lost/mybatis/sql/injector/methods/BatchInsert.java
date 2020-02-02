package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.sql.mapping.insert.InsertSqlBuilder;
import com.wkit.lost.mybatis.sql.method.AbstractInsertMethod;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 批量保存方法映射
 * @author wvkity
 */
public class BatchInsert extends AbstractInsertMethod {

    @Override
    public MappedStatement injectMappedStatement( Class<?> mapperInterface, Class<?> resultType, TableWrapper table ) {
        return addInsertMappedStatement( mapperInterface, resultType, table, new InsertSqlBuilder() );
    }

    @Override
    public String mappedMethod() {
        return "batchInsert";
    }
}
