package com.wkit.lost.mybatis.plugins.dbs.dialect;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

/**
 * 分页方言
 * @author wvkity
 */
public interface PageableDialect extends Dialect {

    /**
     * 执行分页查询前，检查是否执行查询记录总数
     * @param statement {@link MappedStatement}
     * @param parameter 接口参数
     * @param rowBounds 分页参数
     * @return true: 执行查询记录总数 | false: 继续下一步判断
     */
    boolean beforeOfQueryRecord( MappedStatement statement, Object parameter, RowBounds rowBounds );

    /**
     * 总记录数查询执行完成后，检查是否执行分页查询(判断总记录数是否大于0)
     * @param records   总记录数
     * @param parameter 接口参数
     * @param rowBounds 分页参数
     * @return true: 继续执行 | false: 直接返回
     */
    boolean afterOfQueryRecord( long records, Object parameter, RowBounds rowBounds );

}
