package com.wvkity.mybatis.core.mapping.script;

/**
 * XML-SQL脚本创建器
 * @author wvkity
 */
public interface ScriptBuilder {

    /**
     * 构建SQL脚本
     * <pre>
     *   // example:
     *   &lt;script&gt;SELECT * FROM TB_USER WHERE 1 = 1&lt;/script&gt;
     * </pre>
     * @return SQL脚本
     */
    String build();
}
