package com.wkit.lost.mybatis.core.injector.method;

import com.wkit.lost.mybatis.core.mapping.sql.Provider;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import org.apache.ibatis.mapping.MappedStatement;

public abstract class AbstractDeleteMethod extends AbstractMethod {

    /**
     * 添加删除{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param __              返回值类型
     * @param table           表对象
     * @param provider        SQL提供者
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addDeleteMappedStatement( Class<?> mapperInterface, Class<?> __,
                                                        TableWrapper table, Provider provider ) {
        Class<?> entity = table.getEntity();
        return addDeleteMappedStatement( mapperInterface, applyMethod(),
                createSqlSource( createScriptBuilder( table, provider ), entity ), null );
    }
}
