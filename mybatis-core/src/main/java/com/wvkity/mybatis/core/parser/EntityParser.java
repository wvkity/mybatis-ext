package com.wvkity.mybatis.core.parser;

import com.wvkity.mybatis.core.metadata.TableBuilder;
import com.wvkity.mybatis.core.metadata.TableWrapper;

/**
 * 实体解析器
 * @author wvkity
 */
public interface EntityParser {

    /**
     * 解析实体-数据库表映射信息
     * @param builder 数据库表构建器
     * @return 解析实体-数据库表映射对象
     */
    TableWrapper parse(final TableBuilder builder);
}
