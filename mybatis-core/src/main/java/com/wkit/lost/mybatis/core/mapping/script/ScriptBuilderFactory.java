package com.wkit.lost.mybatis.core.mapping.script;

import com.wkit.lost.mybatis.core.mapping.sql.Provider;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;

import java.util.Locale;

/**
 * 脚本构建器工厂
 * @author wvkity
 */
public final class ScriptBuilderFactory {

    private ScriptBuilderFactory() {
    }

    /**
     * 创建脚本构建器
     * @param provider SQL提供者
     * @param table    表包装对象
     * @return 脚本构建器
     */
    public static ScriptBuilder create( final Provider provider, final TableWrapper table ) {
        return create( provider, table, table.getEntity(), null );
    }

    /**
     * 创建脚本构建器
     * @param provider SQL提供者
     * @param table    表包装对象
     * @param entity   实体类
     * @param alias    表别名
     * @return 脚本构建器
     */
    public static ScriptBuilder create( final Provider provider, final TableWrapper table,
                                        final Class<?> entity, final String alias ) {
        return () -> {
            String script = provider.build( table, entity, alias );
            if ( script.toLowerCase( Locale.ENGLISH ).startsWith( "<script>" ) ) {
                return script;
            }
            return "<script>" + script + "</script>";
        };
    }

}
