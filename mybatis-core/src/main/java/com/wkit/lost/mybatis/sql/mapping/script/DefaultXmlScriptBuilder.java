package com.wkit.lost.mybatis.sql.mapping.script;

import com.wkit.lost.mybatis.core.schema.Table;
import com.wkit.lost.mybatis.sql.mapping.SqlBuilder;
import lombok.Getter;

/**
 * 默认XML-SQL脚本构建器
 * @author DT
 */
public class DefaultXmlScriptBuilder extends AbstractXmlScriptBuilder {

    /**
     * 实体类
     */
    @Getter
    private Class<?> entity;

    /**
     * 别名
     */
    @Getter
    private String alias;

    /**
     * 表映射信息
     */
    @Getter
    private Table table;

    /**
     * 构造方法
     * @param entity  实体类
     * @param alias   别名
     * @param table   表映射信息
     * @param builder SQL构建器
     */
    public DefaultXmlScriptBuilder( Class<?> entity, String alias, Table table, SqlBuilder builder ) {
        super( builder );
        this.entity = entity;
        this.alias = alias;
        this.table = table;
    }

    @Override
    public String buildXmlSql() {
        return this.builder.buildSqlString( this.entity, this.table, this.alias );
    }

}
