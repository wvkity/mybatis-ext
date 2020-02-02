package com.wkit.lost.mybatis.sql.method;

import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.sql.mapping.SqlBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.AbstractXmlScriptBuilder;
import com.wkit.lost.mybatis.sql.mapping.script.DefaultXmlScriptBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.StringUtil;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;

import java.util.Optional;

/**
 * 抽象Insert方法模板
 * @author wvkity
 */
public abstract class AbstractInsertMethod extends AbstractMethod {

    /**
     * 创建主键生成器
     * @param table       表映射信息
     * @param statementId {@link MappedStatement}-ID
     * @return {@link KeyGenerator}
     */
    protected KeyGenerator createKeyGenerator( TableWrapper table, String statementId ) {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        KeyGenerator keyGenerator = new NoKeyGenerator();
        // 序列
        String id = statementId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        // 检查是否存在主键
        if ( primaryKey != null ) {
            if ( primaryKey.isIdentity() ) {
                // 自增(数据库自动实现)
                keyGenerator = new Jdbc3KeyGenerator();
                this.configuration.setUseGeneratedKeys( true );
            } else if ( StringUtil.hasText( primaryKey.getSequenceName() ) ) {
                // 创建selectKey映射
                StatementType statementType = StatementType.PREPARED;
                String keyProperty = primaryKey.getProperty();
                String keyColumn = primaryKey.getColumn();
                SqlSource sqlSource = languageDriver.createSqlSource( this.configuration,
                        ColumnConvert.getSequenceScript( this.configuration, primaryKey ), null );
                this.assistant.addMappedStatement( id, sqlSource, statementType, SqlCommandType.SELECT,
                        null, null, null, null, null,
                        primaryKey.getJavaType(), null, false, false,
                        false, new NoKeyGenerator(), keyProperty, keyColumn, null,
                        this.languageDriver, null );
                id = this.assistant.applyCurrentNamespace( id, false );
                MappedStatement ms = this.assistant.getConfiguration().getMappedStatement( id, false );
                // 根据<selectKey>标签获取主键
                keyGenerator = new SelectKeyGenerator( ms, Optional.ofNullable( primaryKey.getExecuting() )
                        .map( Executing::value ).orElse( false ) );
                // useGeneratorKeys = true
                this.assistant.getConfiguration().addKeyGenerator( id, keyGenerator );
                this.assistant.getConfiguration().setUseGeneratedKeys( true );
            }
        }
        return keyGenerator;
    }

    /**
     * 创建脚本构建器
     * @param table      表对象
     * @param sqlBuilder SQL构建器
     * @return 脚本构建器
     */
    protected AbstractXmlScriptBuilder createScriptBuilder( TableWrapper table, SqlBuilder sqlBuilder ) {
        return new DefaultXmlScriptBuilder( table.getEntity(), null, table, sqlBuilder );
    }

    /**
     * 添加插入{@link MappedStatement}对象到MyBatis容器中
     * @param mapperInterface 接口
     * @param __              返回值类型
     * @param table           表对象
     * @param sqlBuilder      SQL构建器
     * @return {@link MappedStatement}对象
     */
    protected MappedStatement addInsertMappedStatement( Class<?> mapperInterface, Class<?> __,
                                                        TableWrapper table, SqlBuilder sqlBuilder ) {
        KeyGenerator keyGenerator = createKeyGenerator( table, ( mapperInterface.getName() + "." + mappedMethod() ) );
        ColumnWrapper primary = table.getPrimaryKey();
        Class<?> entity = table.getEntity();
        AbstractXmlScriptBuilder scriptBuilder = createScriptBuilder( table, sqlBuilder );
        return addInsertMappedStatement( mapperInterface, entity, mappedMethod(),
                createSqlSource( scriptBuilder, entity ), keyGenerator, primary.getProperty(), primary.getColumn() );
    }
}
