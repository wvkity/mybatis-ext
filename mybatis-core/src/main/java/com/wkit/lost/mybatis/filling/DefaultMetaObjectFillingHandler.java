package com.wkit.lost.mybatis.filling;

import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.filling.gen.AbstractGenerator;
import com.wkit.lost.mybatis.filling.gen.DateGenerator;
import com.wkit.lost.mybatis.filling.gen.InstantGenerator;
import com.wkit.lost.mybatis.filling.gen.LocalDateGenerator;
import com.wkit.lost.mybatis.filling.gen.LocalDateTimeGenerator;
import com.wkit.lost.mybatis.filling.gen.LocalTimeGenerator;
import com.wkit.lost.mybatis.filling.gen.OffsetDateTimeGenerator;
import com.wkit.lost.mybatis.filling.gen.OffsetTimeGenerator;
import com.wkit.lost.mybatis.filling.gen.SqlDateGenerator;
import com.wkit.lost.mybatis.filling.gen.TimestampGenerator;
import com.wkit.lost.mybatis.filling.gen.ZonedDateTimeGenerator;
import com.wkit.lost.mybatis.filling.proxy.GeneratorFactory;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 元对象字段自动填充值默认处理器
 * @author DT
 */
@EqualsAndHashCode
@ToString
public class DefaultMetaObjectFillingHandler implements MetaObjectFillingHandler {

    private static final Map<Class<?>, Class<? extends AbstractGenerator>> DATE_TYPE_FILLING_CACHE = new ConcurrentHashMap<>( 10 );
    private static final Set<String> INSERT_TIME_FILLING_PROPERTIES = new HashSet<>( 8 );
    private static final Set<String> UPDATE_TIME_FILLING_PROPERTIES = new HashSet<>( 8 );
    private static final Set<String> DELETE_TIME_FILLING_PROPERTIES = new HashSet<>( 8 );
    private static final Set<String> CREATOR_FILLING_PROPERTIES = new HashSet<>( 8 );
    private static final Set<String> CREATOR_ID_FILLING_PROPERTIES = new HashSet<>( 8 );
    private static final Set<String> MODIFIER_FILLING_PROPERTIES = new HashSet<>( 8 );
    private static final Set<String> MODIFIER_ID_FILLING_PROPERTIES = new HashSet<>( 8 );
    private static final Set<String> DELETED_FILLING_PROPERTIES = new HashSet<>( 8 );
    private static final Set<String> DELETED_ID_FILLING_PROPERTIES = new HashSet<>( 8 );

    static {
        INSERT_TIME_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "gmtCreate", "createTime", "createDate", "createdTime", "createdDate" ) );
        UPDATE_TIME_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "gmtModify", "gmtModified", "modifyTime", "modifiedTime", "updateTime", "updatedTime" ) );
        DELETE_TIME_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "gmtDelete", "gmtDeleted", "gmtDel", "deleteTime", "deletedTime", "delTime" ) );
        CREATOR_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "creator", "createBy", "createUser", "createUserName", "createdBy", "createdUser", "updatedUserName" ) );
        CREATOR_ID_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "creatorId", "createId", "createUserId", "createdId", "createdUserId" ) );
        MODIFIER_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "modifier", "modifyBy", "modifiedBy", "modifyUser", "modifyUserName", "updateBy", "updateUser" ) );
        MODIFIER_ID_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "modifierId", "modifiedId", "modifyId", "modifyUserId", "modifiedUserId", "updateId", "updateUserId" ) );
        DELETED_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "deleteUser", "deleteUserName", "deletedUser", "deletedUserName", "delUser", "delUserName", "delBy", "deleteBy", "deletedBy" ) );
        DELETED_ID_FILLING_PROPERTIES.addAll( ArrayUtil.toList( "delUserId", "delUserId", "deleteUserId", "deletedUserId" ) );
        DATE_TYPE_FILLING_CACHE.put( Date.class, DateGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( java.sql.Date.class, SqlDateGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( Timestamp.class, TimestampGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( LocalTime.class, LocalTimeGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( LocalDate.class, LocalDateGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( LocalDateTime.class, LocalDateTimeGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( OffsetTime.class, OffsetTimeGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( OffsetDateTime.class, OffsetDateTimeGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( Instant.class, InstantGenerator.class );
        DATE_TYPE_FILLING_CACHE.put( ZonedDateTime.class, ZonedDateTimeGenerator.class );
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
    private MetaObjectFillingDependency dependency;

    /**
     * 构造方法
     * @param disableInsert       是否禁用保存操作自动填充
     * @param disableUpdate       是否禁用更新操作自动填充
     * @param disableDelete       是否禁用逻辑删除操作自动填充
     * @param disableAutoMatching 是否禁用自动匹配
     * @param dependency          填充依赖
     */
    public DefaultMetaObjectFillingHandler( boolean disableInsert, boolean disableUpdate, boolean disableDelete,
                                            boolean disableAutoMatching, MetaObjectFillingDependency dependency ) {
        this.disableInsert = disableInsert;
        this.disableUpdate = disableUpdate;
        this.disableDelete = disableDelete;
        this.disableAutoMatching = disableAutoMatching;
        this.dependency = dependency;
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
            if ( columns != null && !columns.isEmpty() ) {
                for ( Column column : columns ) {
                    Class<? extends AbstractGenerator> target = DATE_TYPE_FILLING_CACHE.get( column.getJavaType() );
                    if ( target != null ) {
                        fillingValue( metaObject, column.getProperty(), GeneratorFactory.build( target ) );
                    }
                }
            }
        }
    }

    private void autoFilling( MetaObject metaObject, Table table, Set<String> properties, boolean isUserName ) {
        if ( enableAutoMatching() && table != null && dependency != null && properties != null ) {
            Object value = isUserName ? dependency.currentUserName() : dependency.currentUserId();
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
                    Class<? extends AbstractGenerator> target = DATE_TYPE_FILLING_CACHE.get( column.getJavaType() );
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
