package com.wvkity.mybatis.utils;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * MappedStatement工具
 * @author wvkity
 */
public abstract class MappedStatementUtil {

    /***
     * 获取已存在的{@link MappedStatement}
     * @param configuration 配置对象
     * @param msId {@link MappedStatement}-ID
     * @return {@link MappedStatement}对象
     */
    public static MappedStatement getExistsMappedStatement(final Configuration configuration, final String msId) {
        try {
            return configuration.getMappedStatement(msId, false);
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    /**
     * 创建查询总记录{@link MappedStatement}对象
     * @param statement {@link MappedStatement}
     * @param newMsId   新{@link MappedStatement}ID
     * @return {@link MappedStatement}对象
     */
    public static MappedStatement newQueryRecordMappedStatement(MappedStatement statement, String newMsId) {
        MappedStatement.Builder builder = new MappedStatement.Builder(statement.getConfiguration(), newMsId, statement.getSqlSource(), statement.getSqlCommandType());
        builder.resource(statement.getResource());
        builder.fetchSize(statement.getFetchSize());
        builder.statementType(statement.getStatementType());
        builder.keyGenerator(statement.getKeyGenerator());
        if (!ArrayUtil.isEmpty(statement.getKeyProperties())) {
            builder.keyProperty(String.join(",", statement.getKeyProperties()));
        }
        builder.timeout(statement.getTimeout());
        builder.parameterMap(statement.getParameterMap());
        List<ResultMap> resultMaps = new ArrayList<>();
        ResultMap resultMap = new ResultMap.Builder(statement.getConfiguration(), statement.getId(), Long.class, new ArrayList<>(0)).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(statement.getResultSetType());
        builder.cache(statement.getCache());
        builder.flushCacheRequired(statement.isFlushCacheRequired());
        builder.useCache(statement.isUseCache());
        return builder.build();
    }
}
