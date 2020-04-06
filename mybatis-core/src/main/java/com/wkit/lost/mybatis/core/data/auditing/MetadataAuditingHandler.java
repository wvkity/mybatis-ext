package com.wkit.lost.mybatis.core.data.auditing;

import com.wkit.lost.mybatis.core.data.auditing.time.provider.DateTimeProvider;
import com.wkit.lost.mybatis.core.data.auditing.time.proxy.DateTimeProviderFactory;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 默认元数据审计处理器
 * @author wvkity
 */
@Log4j2
@Setter
public class MetadataAuditingHandler extends AbstractMetadataAuditable {

    private static final String CREATED_TIME_STRING = "gmtCreate,gmtCreated,createTime,createdTime,createDate,createdDate";
    private static final String CREATED_USER_STRING = "creator,createUserName,createdUserName,createBy,createdBy";
    private static final String CREATED_USER_ID_STRING = "createUserId,createdUserId";
    private static final String MODIFIED_TIME_STRING = "gmtModified,gmtLastModified,lastModifiedTime,lastModifiedDate," +
            "modifiedTime,modifiedDate,updateTime,updatedTime,updateDate,updatedDate";
    private static final String MODIFIED_USER_STRING = "modifier,modifiedUserName,lastModifiedUserName," +
            "modifiedBy,lastModifiedBy,updateUserName,updatedUserName,updateBy,updatedBy";
    private static final String MODIFIED_USER_ID_STRING = "modifiedUserId,lastModifiedUserId,updateUserId,updatedUserId";
    private static final String DELETED_TIME_STRING = "gmtDeleted,gmtDelete,deletedTime,deleteTime,deletedDate," +
            "deleteDate,delTime,delDate";
    private static final String DELETED_USER_STRING = "deleteUserName,deletedUserName,delUserName,deletedBy,deleteBy";
    private static final String DELETED_USER_ID_STRING = "deleteUserId,deletedUserId,delUserId";

    private static final Set<String> AUDITING_PROP_CREATED_TIME =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( CREATED_TIME_STRING.split( "," ) ) ) );
    private static final Set<String> AUDITING_PROP_CREATED_USER =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( CREATED_USER_STRING.split( "," ) ) ) );
    private static final Set<String> AUDITING_PROP_CREATED_USER_ID =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( CREATED_USER_ID_STRING.split( "," ) ) ) );
    private static final Set<String> AUDITING_PROP_MODIFIED_TIME =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( MODIFIED_TIME_STRING.split( "," ) ) ) );
    private static final Set<String> AUDITING_PROP_MODIFIED_USER =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( MODIFIED_USER_STRING.split( "," ) ) ) );
    private static final Set<String> AUDITING_PROP_MODIFIED_USER_ID =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( MODIFIED_USER_ID_STRING.split( "," ) ) ) );
    private static final Set<String> AUDITING_PROP_DELETED_TIME =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( DELETED_TIME_STRING.split( "," ) ) ) );
    private static final Set<String> AUDITING_PROP_DELETED_USER =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( DELETED_USER_STRING.split( "," ) ) ) );
    private static final Set<String> AUDITING_PROP_DELETED_USER_ID =
            Collections.unmodifiableSet( new HashSet<>( Arrays.asList( DELETED_USER_ID_STRING.split( "," ) ) ) );

    private boolean inserted;
    private boolean modified;
    private boolean deleted;
    private boolean automatic;
    private AuditorAware auditorAware;

    @Override
    public void inserted( MetaObject metadata ) {
        Optional.ofNullable( getTable( metadata ) ).ifPresent( it ->
                auditing( metadata, it.insertedAuditableColumns(), AuditMatching.INSERTED ) );
    }

    @Override
    public void modified( MetaObject metadata ) {
        Optional.ofNullable( getTable( metadata ) ).ifPresent( it ->
                auditing( metadata, it.modifiedAuditableColumns(), AuditMatching.MODIFIED ) );
    }

    @Override
    public void deleted( MetaObject metadata ) {
        Optional.ofNullable( getTable( metadata ) ).ifPresent( it ->
                auditing( metadata, it.deletedAuditableColumns(), AuditMatching.DELETED ) );
    }

    private void auditing( MetaObject metadata, Set<ColumnWrapper> columns, AuditMatching matching ) {
        boolean dateAuditing = false;
        boolean userAuditing = false;
        boolean userNameAuditing = false;
        Optional<AuditorAware> auditorAwareOptional = Optional.ofNullable( auditorAware );
        boolean isPresent = auditorAwareOptional.isPresent();
        if ( isNotEmpty( columns ) ) {
            for ( ColumnWrapper column : columns ) {
                if ( isDateAuditable( column ) ) {
                    dateAuditing = true;
                    dateTimeAuditing( metadata, column, matching );
                } else if ( isPresent ) {
                    if ( isUserAuditable( column ) ) {
                        userAuditing = true;
                        invoke( metadata, column.getProperty(),
                                auditorAwareOptional.map( AuditorAware::currentUserId )
                                        .orElse( null ), matching );
                    } else if ( isUserNameAuditable( column ) ) {
                        userNameAuditing = true;
                        invoke( metadata, column.getProperty(),
                                auditorAwareOptional.map( AuditorAware::currentUserName )
                                        .orElse( null ), matching );
                    }
                }
            }
        }
        if ( this.enableAutomaticAuditable() ) {
            // 自动审计
            switch ( matching ) {
                case INSERTED:
                    insertedAutoAuditing( metadata, !dateAuditing, !userAuditing, !userNameAuditing );
                    break;
                case MODIFIED:
                    modifiedAutoAuditing( metadata, !dateAuditing, !userAuditing, !userNameAuditing );
                    break;
                case DELETED:
                    deletedAutoAuditing( metadata, !dateAuditing, !userAuditing, !userNameAuditing );
                    break;
            }
        }
    }

    /**
     * 保存操作自动审计
     * @param metadata             元数据对象
     * @param execDateAuditing     时间审计
     * @param execUserAuditing     用户标识审计
     * @param execUserNameAuditing 用户名审计
     */
    private void insertedAutoAuditing( MetaObject metadata, boolean execDateAuditing, boolean execUserAuditing,
                                       boolean execUserNameAuditing ) {
        dateTimeAuditing( metadata, AUDITING_PROP_CREATED_TIME, execDateAuditing );
        Optional<AuditorAware> it = Optional.ofNullable( auditorAware );
        otherAuditing( metadata, AUDITING_PROP_CREATED_USER_ID, execUserAuditing,
                it.map( AuditorAware::currentUserId ).orElse( null ) );
        otherAuditing( metadata, AUDITING_PROP_CREATED_USER, execUserNameAuditing,
                it.map( AuditorAware::currentUserName ).orElse( null ) );
    }

    /**
     * 保存操作自动审计
     * @param metadata             元数据对象
     * @param execDateAuditing     时间审计
     * @param execUserAuditing     用户标识审计
     * @param execUserNameAuditing 用户名审计
     */
    private void modifiedAutoAuditing( MetaObject metadata, boolean execDateAuditing, boolean execUserAuditing,
                                       boolean execUserNameAuditing ) {
        dateTimeAuditing( metadata, AUDITING_PROP_MODIFIED_TIME, execDateAuditing );
        Optional<AuditorAware> it = Optional.ofNullable( auditorAware );
        otherAuditing( metadata, AUDITING_PROP_MODIFIED_USER_ID, execUserAuditing,
                it.map( AuditorAware::currentUserId ).orElse( null ) );
        otherAuditing( metadata, AUDITING_PROP_MODIFIED_USER, execUserNameAuditing,
                it.map( AuditorAware::currentUserName ).orElse( null ) );
    }

    /**
     * 保存操作自动审计
     * @param metadata             元数据对象
     * @param execDateAuditing     时间审计
     * @param execUserAuditing     用户标识审计
     * @param execUserNameAuditing 用户名审计
     */
    private void deletedAutoAuditing( MetaObject metadata, boolean execDateAuditing, boolean execUserAuditing,
                                      boolean execUserNameAuditing ) {
        dateTimeAuditing( metadata, AUDITING_PROP_DELETED_TIME, execDateAuditing );
        Optional<AuditorAware> it = Optional.ofNullable( auditorAware );
        otherAuditing( metadata, AUDITING_PROP_DELETED_USER_ID, execUserAuditing,
                it.map( AuditorAware::currentUserId ).orElse( null ) );
        otherAuditing( metadata, AUDITING_PROP_DELETED_USER, execUserNameAuditing,
                it.map( AuditorAware::currentUserName ).orElse( null ) );
    }

    /**
     * 时间类型数据审计
     * @param metadata   元数据
     * @param properties 审计属性列表
     * @param exec       是否可审计标识
     */
    private void dateTimeAuditing( MetaObject metadata, Set<String> properties,
                                   boolean exec ) {
        if ( exec ) {
            Optional.ofNullable( getTable( metadata ) ).ifPresent( it -> {
                for ( String property : properties ) {
                    Optional<ColumnWrapper> optional = it.search( property );
                    if ( optional.isPresent() ) {
                        ColumnWrapper column = optional.get();
                        Optional<DateTimeProvider> provider =
                                Optional.ofNullable( DateTimeProviderFactory.ProviderBuilder.create()
                                        .target( column.getJavaType() ).build() );
                        provider.ifPresent( pt -> invoke( metadata, column.getProperty(), pt.getNow() ) );
                        break;
                    }
                }
            } );
        }
    }

    /**
     * 其他类型数据审计
     * @param metadata   元数据
     * @param properties 审计属性列表
     * @param exec       是否可审计
     * @param value      值
     */
    private void otherAuditing( MetaObject metadata, Set<String> properties, boolean exec,
                                Object value ) {
        if ( exec && auditorAware != null && value != null ) {
            Optional.ofNullable( getTable( metadata ) ).ifPresent( it -> {
                for ( String property : properties ) {
                    Optional<ColumnWrapper> optional = it.search( property );
                    if ( optional.isPresent() ) {
                        invoke( metadata, property, value );
                        break;
                    }
                }
            } );
        }
    }

    private boolean isDateAuditable( ColumnWrapper column ) {
        return column.isCreatedDate() || column.isLastModifiedDate() || column.isDeletedDate();
    }

    private boolean isUserAuditable( ColumnWrapper column ) {
        return column.isCreatedUser() || column.isLastModifiedUser() || column.isDeletedUser();
    }

    private boolean isUserNameAuditable( ColumnWrapper column ) {
        return column.isCreatedUserName() || column.isLastModifiedUserName() || column.isDeletedUserName();
    }

    private boolean isNotEmpty( Collection<?> collection ) {
        return collection != null && !collection.isEmpty();
    }

    @Override
    public boolean enableInsertedAuditable() {
        return this.inserted;
    }

    @Override
    public boolean enableModifiedAuditable() {
        return this.modified;
    }

    @Override
    public boolean enableDeletedAuditable() {
        return this.deleted;
    }

    @Override
    public boolean enableAutomaticAuditable() {
        return this.automatic;
    }
}
