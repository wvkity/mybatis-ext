package com.wkit.lost.mybatis.sql.mapping.script;

import com.wkit.lost.mybatis.sql.mapping.SqlBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 抽象XML-SQL脚本构建器
 * @author wvkity
 */
@AllArgsConstructor
public abstract class AbstractXmlScriptBuilder {

    /**
     * SQL构建器
     */
    @Getter
    protected SqlBuilder builder;

    /**
     * 构建XML-SQL语句
     * @return XML-SQL字符串
     */
    public abstract String buildXmlSql();

    /**
     * 构建SQL脚本
     * <p>
     *     eg: <br>
     *         &lt;script&gt;SELECT * FROM TB_USER&lt;/script&gt;
     * </p>
     * @return SQL脚本
     */
    public String build() {
        String script = this.buildXmlSql();
        if ( script.startsWith( "<script>" ) ) {
            return script;
        }
        return "<script>" + script + "</script>";
    }

}
