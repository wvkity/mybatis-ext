package com.wkit.lost.mybatis.plugins.dbs.dialect;

import com.wkit.lost.mybatis.plugins.executor.Argument;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * 数据库方言
 * @author DT
 */
public interface Dialect {

    String PAGEABLE_RECORD_SUFFIX = ".customInlineCount";
    String PAGEABLE_PREFIX = "pageable";
    String OFFSET_PARAMETER = PAGEABLE_PREFIX + "Offset";
    String LIMIT_PARAMETER = PAGEABLE_PREFIX + "Limit";

    /**
     * 设置属性
     * @param props 属性
     */
    void setProperties( Properties props );

    /**
     * 过滤分页查询
     * @param statement {@link MappedStatement}
     * @param parameter 方法参数
     * @param rowBounds 分页参数
     * @return true: 执行 | false: 跳出
     */
    boolean filter( MappedStatement statement, Object parameter, RowBounds rowBounds );

    /**
     * 处理参数
     * @param arg 参数对象
     * @return 新的对象
     */
    Object processParameter( Argument arg );

    /**
     * 生成分页SQL语句
     * @param arg 参数对象
     * @return SQL
     */
    String generatePageableSql( Argument arg );

    /**
     * 执行分页查询后，处理分页结果
     * @param result 结果
     * @param arg    参数对象
     * @return 结果
     */
    Object executePagingOnAfter( List result, Argument arg );

    /**
     * 所有查询执行完成后
     */
    void completed();
}
