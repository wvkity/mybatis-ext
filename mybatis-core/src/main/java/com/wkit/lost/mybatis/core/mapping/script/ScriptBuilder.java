package com.wkit.lost.mybatis.core.mapping.script;

/**
 * XML-SQL脚本创建器
 * @author wvkity
 */
public interface ScriptBuilder {

    /**
     * 构建SQL脚本
     * <p>
     *     example:
     *     <p>&lt;script&gt;SELECT * FROM TB_USER WHERE 1 = 1&lt;/script&gt;</p>
     * </p>
     * @return SQL脚本
     */
    String build();
}
