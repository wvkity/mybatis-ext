package com.wkit.lost.mybatis.core.injector.execute;

import com.wkit.lost.mybatis.core.injector.method.AbstractInsertMethod;
import com.wkit.lost.mybatis.core.mapping.sql.insert.InsertProvider;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 批量保存操作方法映射(不自动审计)
 * @author wvkity
 */
public class BatchInsertNotWithAudit extends AbstractInsertMethod {

    @Override
    public MappedStatement injectMappedStatement(TableWrapper table, Class<?> mapperInterface, Class<?> resultType) {
        return addInsertMappedStatement(mapperInterface, resultType, table, new InsertProvider());
    }
}
