package com.wkit.lost.mybatis.incrementer;

import com.wkit.lost.mybatis.annotation.extension.Dialect;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 序列SQL枚举类
 * @author wvkity
 */
public enum SequenceKeyGenerator {

    /**
     * ORACLE
     */
    ORACLE {
        @Override
        public String toSqlString(String sequenceName) {
            return "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
        }
    },

    /**
     * DB2
     */
    DB2 {
        @Override
        public String toSqlString(String sequenceName) {
            return "values nextval for " + sequenceName;
        }
    },

    /**
     * H2
     */
    H2 {
        @Override
        public String toSqlString(String sequenceName) {
            return "select " + sequenceName + ".nextval";
        }
    },

    /**
     * POSTGRE
     */
    POSTGRE {
        @Override
        public String toSqlString(String sequenceName) {
            return "select nextval('" + sequenceName + "')";
        }
    };

    /**
     * 缓存
     */
    private static final Map<String, SequenceKeyGenerator> CACHE = Stream.of(ORACLE, DB2, H2, POSTGRE)
            .collect(Collectors.toConcurrentMap(Enum::name, Function.identity()));

    /**
     * 获取序列实例
     * @param dialect 数据类型
     * @return 序列实例
     */
    public static SequenceKeyGenerator getInstance(Dialect dialect) {
        return getInstance(dialect.name());
    }

    /**
     * 获取序列实例
     * @param dialect 数据类型
     * @return 序列实例
     */
    public static SequenceKeyGenerator getInstance(String dialect) {
        return CACHE.get(dialect);
    }

    /**
     * 序列转SQL语句
     * @param sequenceName 序列名称
     * @return SQL语句
     */
    public abstract String toSqlString(final String sequenceName);
}
