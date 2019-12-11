package com.wkit.lost.mybatis.filling;

import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.filling.gen.AbstractGenerator;
import com.wkit.lost.mybatis.filling.proxy.GeneratorFactory;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.reflection.MetaObject;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 元对象字段自动填充值默认处理器
 * @author wvkity
 */
@EqualsAndHashCode
@ToString
public class DefaultMetaObjectFillingHandler implements MetaObjectFillingHandler {

    
    /**
     * 创建时间属性列表
     */
    private static final Set<String> INSERT_TIME_FILLING_PROPERTIES = new HashSet<>( 8 );
    /**
     * 更新时间属性列表
     */
    private static final Set<String> UPDATE_TIME_FILLING_PROPERTIES = new HashSet<>( 8 );
    /**
     * 删除时间属性列表
     */
    private static final Set<String> DELETE_TIME_FILLING_PROPERTIES = new HashSet<>( 8 );
    /**
     * 创建人属性列表
     */
    private static final Set<String> CREATOR_FILLING_PROPERTIES = new HashSet<>( 8 );
    /**
     * 创建人ID属性列表
     */
    private static final Set<String> CREATOR_ID_FILLING_PROPERTIES = new HashSet<>( 8 );
    /**
     * 更新人属性列表
     */
    private static final Set<String> MODIFIER_FILLING_PROPERTIES = new HashSet<>( 8 );
    /**
     * 更新人ID属性列表
     */
    private static final Set<String> MODIFIER_ID_FILLING_PROPERTIES = new HashSet<>( 8 );
    /**
     * 删除人属性列表
     */
    private static final Set<String> DELETED_FILLING_PROPERTIES = new HashSet<>( 8 );
    /**
     * 删除人ID属性列表
     */
    private static final Set<String> DELETED_ID_FILLING_PROPERTIES = new HashSet<>( 8 );

    static {
        INSERT_TIME_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "gmtCreate", "createTime", "createDate", "createdTime", "createdDate" ) );
        UPDATE_TIME_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "gmtModify", "gmtModified", "modifyTime", "modifiedTime", "updateTime", "updatedTime" ) );
        DELETE_TIME_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "gmtDelete", "gmtDel", "deleteTime", "deletedTime", "delTime" ) );
        CREATOR_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "creator", "createBy", "createUser", "createUserName", "createdBy", "createdUser" ) );
        CREATOR_ID_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "creatorId", "createId", "createUserId", "createdId", "createdUserId" ) );
        MODIFIER_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "modifier", "modifyUser", "modifyUserName", "updateUser", "updatedUserName" ) );
        MODIFIER_ID_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "modifierId", "modifiedId", "modifyUserId", "modifiedUserId", "updateUserId" ) );
        DELETED_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "deleteUser", "deleteUserName", "deletedUser", "deletedUserName", "delUser", "delUserName" ) );
        DELETED_ID_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "deleteUserId", "deletedUserId" ) );
        
    }

    /**
     * 是否禁用保存操作自动填充
     */
    private boolean disableInsert;
    /**
     * 是否禁用更新操作自动填充
     */
    private boolean disableUpdate;
    /**
     * 是否禁用逻辑删除操作自动填充
     */
    private boolean disableDelete;

    /**
     * 是否禁用自动匹配(自定义模式)
     */
    private boolean disableAutoMatching;

    /**
     * 填充依赖
     */
    private MetaObjectFillAuxiliary auxiliary;

    /**
     * 构造方法
     * @param disableInsert       是否禁用保存操作自动填充
     * @param disableUpdate       是否禁用更新操作自动填充
     * @param disableDelete       是否禁用逻辑删除操作自动填充
     * @param disableAutoMatching 是否禁用自动匹配
     * @param auxiliary           填充辅助
     */
    public DefaultMetaObjectFillingHandler( boolean disableInsert, boolean disableUpdate, boolean disableDelete,
                                            boolean disableAutoMatching, MetaObjectFillAuxiliary auxiliary ) {
        this.disableInsert = disableInsert;
        this.disableUpdate = disableUpdate;
        this.disableDelete = disableDelete;
        this.disableAutoMatching = disableAutoMatching;
        this.auxiliary = auxiliary;
    }

    @Override
    public void insertFilling( MetaObject metaObject ) {
        Table table = getTable( metaObject );
        if ( table != null ) {
            // 获取所有自动填充字段
            Set<Column> columns = table.getInsertFillings();
            requireFilling( metaObject, columns );
            // 自动匹配
            autoFilling( metaObject, table, CREATOR_FILLING_PROPERTIES, true );
            autoFilling( metaObject, table, CREATOR_ID_FILLING_PROPERTIES, false );
            autoFilingDateTime( metaObject, table, INSERT_TIME_FILLING_PROPERTIES );
        }
    }

    @Override
    public void updateFilling( MetaObject metaObject ) {
        Table table = getTable( metaObject );
        if ( table != null ) {
            // 获取所有自动填充字段
            Set<Column> columns = table.getUpdateFillings();
            requireFilling( metaObject, columns );
            // 自动匹配
            autoFilling( metaObject, table, MODIFIER_FILLING_PROPERTIES, true );
            autoFilling( metaObject, table, MODIFIER_ID_FILLING_PROPERTIES, false );
            autoFilingDateTime( metaObject, table, UPDATE_TIME_FILLING_PROPERTIES );
        }
    }

    @Override
    public void deleteFilling( MetaObject metaObject ) {
        Table table = getTable( metaObject );
        if ( table != null ) {
            // 获取所有自动填充字段
            Set<Column> columns = table.getDeleteFillings();
            requireFilling( metaObject, columns );
            // 自动匹配
            autoFilling( metaObject, table, DELETED_FILLING_PROPERTIES, true );
            autoFilling( metaObject, table, DELETED_ID_FILLING_PROPERTIES, false );
            autoFilingDateTime( metaObject, table, DELETE_TIME_FILLING_PROPERTIES );
        }
    }

    private void requireFilling( MetaObject metaObject, Set<Column> columns ) {
        if ( metaObject != null && columns != null ) {
            if ( !columns.isEmpty() ) {
                for ( Column column : columns ) {
                    Class<? extends AbstractGenerator> target = GeneratorFactory.getTimeGenerator( column.getJavaType() );
                    if ( target != null ) {
                        fillingValue( metaObject, column.getProperty(), GeneratorFactory.build( target ) );
                    }
                }
            }
        }
    }

    private void autoFilling( MetaObject metaObject, Table table, Set<String> properties, boolean isUserName ) {
        if ( enableAutoMatching() && table != null && auxiliary != null && properties != null ) {
            Object value = isUserName ? auxiliary.operator() : auxiliary.operationUserId();
            if ( value != null ) {
                for ( String property : properties ) {
                    Optional<Column> optional = table.search( property );
                    if ( optional.isPresent() ) {
                        fillingValue( metaObject, property, value );
                        break;
                    }
                }
            }
        }
    }

    private void autoFilingDateTime( MetaObject metaObject, Table table, Set<String> properties ) {
        if ( enableAutoMatching() && table != null && properties != null ) {
            for ( String property : properties ) {
                Optional<Column> optional = table.search( property );
                if ( optional.isPresent() ) {
                    Column column = optional.get();
                    Class<? extends AbstractGenerator> target = GeneratorFactory.getTimeGenerator( column.getJavaType() );
                    if ( target != null ) {
                        fillingValue( metaObject, property, GeneratorFactory.build( target ) );
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean enableInsert() {
        return !this.disableInsert;
    }

    @Override
    public boolean enableUpdate() {
        return !this.disableUpdate;
    }

    @Override
    public boolean enableDelete() {
        return !this.disableDelete;
    }

    @Override
    public boolean enableAutoMatching() {
        return !this.disableAutoMatching;
    }
}
