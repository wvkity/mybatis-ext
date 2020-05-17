package com.wvkity.mybatis.incrementer;

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
        public String getScript(String sequenceName) {
            return "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
        }
    },

    /**
     * DB2
     */
    DB2 {
        @Override
        public String getScript(String sequenceName) {
            return "values nextval for " + sequenceName;
        }
    },

    /**
     * H2
     */
    H2 {
        @Override
        public String getScript(String sequenceName) {
            return "select " + sequenceName + ".nextval";
        }
    },

    /**
     * POSTGRESQL
     */
    POSTGRESQL {
        @Override
        public String getScript(String sequenceName) {
            return "select nextval('" + sequenceName + "')";
        }
    };

    /**
     * 获取sql脚本
     * @param sequenceName 序列名称
     * @return sql脚本
     */
    public abstract String getScript(final String sequenceName);
}
