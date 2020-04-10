package com.wkit.lost.mybatis.plugins.paging.dbs.dialect.exact;

import com.wkit.lost.mybatis.plugins.paging.dbs.dialect.AbstractPageableDialect;
import com.wkit.lost.mybatis.utils.MetaObjectUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HsqldbDialect extends AbstractPageableDialect {

    @Override
    public Object processPageableParameter(MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long offset) {
        parameter.put(OFFSET_PARAMETER, offset);
        parameter.put(LIMIT_PARAMETER, rowStart);
        // 处理cacheKey
        cacheKey.update(offset);
        cacheKey.update(rowStart);
        // 处理参数
        if (boundSql.getParameterMappings() != null) {
            List<ParameterMapping> parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
            if (offset > 0) {
                parameterMappings.add(new ParameterMapping.Builder(statement.getConfiguration(), OFFSET_PARAMETER, Long.class).build());
            }
            if (rowStart >= 0) {
                parameterMappings.add(new ParameterMapping.Builder(statement.getConfiguration(), LIMIT_PARAMETER, Long.class).build());
            }
            MetaObject metaObject = MetaObjectUtil.forObject(boundSql);
            metaObject.setValue("parameterMappings", parameterMappings);
        }
        return parameter;
    }

    @Override
    public String generateCorrespondPageableSql(String sql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long pageSize) {
        StringBuilder builder = new StringBuilder(sql.length() + 20);
        builder.append(sql);
        if (pageSize > 0) {
            builder.append(" LIMIT ? ");
        }
        if (rowStart >= 0) {
            builder.append(" OFFSET ? ");
        }
        return builder.toString();
    }
}
