package com.wkit.lost.mybatis.plugins.pagination.dialect;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
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
     * 过滤分页查询
     * @param statement {@link MappedStatement}
     * @param parameter 方法参数
     * @param rowBounds 分页参数
     * @return true: 跳过 | false: 执行
     */
    boolean filter( MappedStatement statement, Object parameter, RowBounds rowBounds );

    /**
     * 执行分页查询前，检查是否执行查询记录总数
     * @param statement {@link MappedStatement}
     * @param parameter 接口参数
     * @param rowBounds 分页参数
     * @return true: 执行查询记录总数 | false: 继续下一步判断
     */
    boolean beforeOfQueryRecord( MappedStatement statement, Object parameter, RowBounds rowBounds );

    /**
     * 生成查询总记录数SQL语句
     * @param statement {@link MappedStatement}
     * @param boundSql  SQL绑定对象
     * @param parameter 接口参数
     * @param rowBounds 分页参数
     * @param cacheKey  缓存Key对象
     * @return 查询总记录数SQL
     */
    String generateQueryRecordSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey );

    /**
     * 总记录数查询执行完成后，检查是否执行分页查询(判断总记录数是否大于0)
     * @param records   总记录数
     * @param parameter 接口参数
     * @param rowBounds 分页参数
     * @return true: 继续执行 | false: 直接返回
     */
    boolean afterOfQueryRecord( long records, Object parameter, RowBounds rowBounds );

    /**
     * 处理查询参数对象
     * @param statement {@link MappedStatement}
     * @param boundSql  SQL绑定对象
     * @param parameter 接口参数
     * @param cacheKey  缓存Key对象
     * @return 新的对象
     */
    Object processParameter( MappedStatement statement, BoundSql boundSql, Object parameter, CacheKey cacheKey );

    /**
     * 执行分页查询前，检查是否需要分页查询
     * @param statement {@link MappedStatement}
     * @param parameter 接口参数
     * @param rowBounds 分页参数
     * @return true: 执行 | false: 返回查询结果
     */
    boolean executePagingOnBefore( MappedStatement statement, Object parameter, RowBounds rowBounds );

    /**
     * 生成分页SQL语句
     * @param statement {@link MappedStatement}
     * @param boundSql  SQL绑定对象
     * @param parameter 接口参数
     * @param rowBounds 分页参数
     * @param cacheKey  缓存Key对象
     * @return 分页SQL
     */
    String generatePageableSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey );

    /**
     * 执行分页查询后，处理分页结果
     * @param result    分页查询结果
     * @param parameter 接口查询
     * @param rowBounds 分页参数
     * @return 结果
     */
    Object executePagingOnAfter( List result, Object parameter, RowBounds rowBounds );

    /**
     * 所有查询执行完成后
     */
    void completed();

    /**
     * 设置属性
     * @param props 属性
     */
    void setProperties( Properties props );
}
