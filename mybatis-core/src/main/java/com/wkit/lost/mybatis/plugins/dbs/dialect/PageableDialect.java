package com.wkit.lost.mybatis.plugins.dbs.dialect;

import com.wkit.lost.mybatis.plugins.executor.Argument;

/**
 * 分页方言
 * @author DT
 */
public interface PageableDialect extends Dialect {

    /**
     * 执行分页查询前，检查是否执行查询记录总数
     * @return true: 执行查询记录总数 | false: 继续下一步判断
     */
    boolean beforeOfQueryRecord( Argument arg );

    /**
     * 生成查询总记录数SQL语句
     * @return 查询总记录数SQL
     */
    String generateQueryRecordSql( Argument arg );

    /**
     * 总记录数查询执行完成后，检查是否执行分页查询(判断总记录数是否大于0)
     * @param records   总记录数
     * @return true: 继续执行 | false: 直接返回
     */
    boolean afterOfQueryRecord( long records, Argument arg );

    /**
     * 执行分页查询前，检查是否需要分页查询
     * @return true: 执行 | false: 返回查询结果
     */
    boolean executePagingOnBefore( Argument arg );
}
