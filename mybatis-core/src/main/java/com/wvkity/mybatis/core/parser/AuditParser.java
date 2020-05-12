package com.wvkity.mybatis.core.parser;

import com.wvkity.mybatis.core.data.auditing.AuditMatching;
import com.wvkity.mybatis.core.data.auditing.AuditType;
import com.wvkity.mybatis.core.metadata.FieldWrapper;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 审计属性解析器
 * @author wvkity
 */
public final class AuditParser {

    // 自动审计属性
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

    /**
     * 创建操作时间属性
     */
    private static final Set<String> AUDITING_PROP_CREATED_TIME =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(CREATED_TIME_STRING.split(","))));
    /**
     * 创建操作人名称属性
     */
    private static final Set<String> AUDITING_PROP_CREATED_USER =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(CREATED_USER_STRING.split(","))));
    /**
     * 创建操作人标识属性
     */
    private static final Set<String> AUDITING_PROP_CREATED_USER_ID =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(CREATED_USER_ID_STRING.split(","))));
    /**
     * 更新操作时间属性
     */
    private static final Set<String> AUDITING_PROP_MODIFIED_TIME =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(MODIFIED_TIME_STRING.split(","))));
    /**
     * 更新操作人名称属性
     */
    private static final Set<String> AUDITING_PROP_MODIFIED_USER =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(MODIFIED_USER_STRING.split(","))));
    /**
     * 更新操作人标识属性
     */
    private static final Set<String> AUDITING_PROP_MODIFIED_USER_ID =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(MODIFIED_USER_ID_STRING.split(","))));
    /**
     * 操作删除时间属性
     */
    private static final Set<String> AUDITING_PROP_DELETED_TIME =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(DELETED_TIME_STRING.split(","))));
    /**
     * 操作删除人名称属性
     */
    private static final Set<String> AUDITING_PROP_DELETED_USER =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(DELETED_USER_STRING.split(","))));
    /**
     * 操作删除人标识属性
     */
    private static final Set<String> AUDITING_PROP_DELETED_USER_ID =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(DELETED_USER_ID_STRING.split(","))));
    /**
     * 时间属性缓存
     */
    private static final Map<AuditMatching, Set<String>> AUDIT_TIME_PROP_CACHE = new ConcurrentHashMap<>(4);
    /**
     * 用户标识属性缓存
     */
    private static final Map<AuditMatching, Set<String>> AUDIT_USER_ID_PROP_CACHE = new ConcurrentHashMap<>(4);
    /**
     * 用户名属性缓存
     */
    private static final Map<AuditMatching, Set<String>> AUDIT_USER_NAME_PROP_CACHE = new ConcurrentHashMap<>(4);

    static {
        // 时间
        AUDIT_TIME_PROP_CACHE.put(AuditMatching.INSERTED, AUDITING_PROP_CREATED_TIME);
        AUDIT_TIME_PROP_CACHE.put(AuditMatching.MODIFIED, AUDITING_PROP_MODIFIED_TIME);
        AUDIT_TIME_PROP_CACHE.put(AuditMatching.DELETED, AUDITING_PROP_DELETED_TIME);
        // 用户名
        AUDIT_USER_NAME_PROP_CACHE.put(AuditMatching.INSERTED, AUDITING_PROP_CREATED_USER);
        AUDIT_USER_NAME_PROP_CACHE.put(AuditMatching.MODIFIED, AUDITING_PROP_MODIFIED_USER);
        AUDIT_USER_NAME_PROP_CACHE.put(AuditMatching.DELETED, AUDITING_PROP_DELETED_USER);
        // 用户标识
        AUDIT_USER_ID_PROP_CACHE.put(AuditMatching.INSERTED, AUDITING_PROP_CREATED_USER_ID);
        AUDIT_USER_ID_PROP_CACHE.put(AuditMatching.MODIFIED, AUDITING_PROP_MODIFIED_USER_ID);
        AUDIT_USER_ID_PROP_CACHE.put(AuditMatching.DELETED, AUDITING_PROP_DELETED_USER_ID);
    }

    /**
     * 匹配审计字段
     * @param field       属性包装对象
     * @param auditType   审计类型
     * @param matching    审计模式
     * @param autoDiscern 是否自动匹配
     * @param annotation  注解类
     * @return true: 是 false: 否
     */
    private static boolean matchAudit(FieldWrapper field, AuditType auditType, AuditMatching matching,
                                      boolean autoDiscern, Class<? extends Annotation> annotation) {
        if (field.isAnnotationPresent(annotation)) {
            return true;
        }
        if (autoDiscern) {
            Set<String> props;
            switch (auditType) {
                case TIME:
                    props = AUDIT_TIME_PROP_CACHE.get(matching);
                    break;
                case USER_ID:
                    props = AUDIT_USER_ID_PROP_CACHE.get(matching);
                    break;
                case USER_NAME:
                    props = AUDIT_USER_NAME_PROP_CACHE.get(matching);
                    break;
                default:
                    return false;
            }
            return props.stream().anyMatch(it -> it.equals(field.getName()));
        }
        return false;
    }

    /**
     * 匹配时间审计字段
     * @param field       属性包装对象
     * @param matching    审计模式
     * @param autoDiscern 是否自动匹配
     * @param annotation  注解类
     * @return true: 是 false: 否
     */
    public static boolean matchAuditForTime(FieldWrapper field, AuditMatching matching,
                                            boolean autoDiscern, Class<? extends Annotation> annotation) {
        return matchAudit(field, AuditType.TIME, matching, autoDiscern, annotation);
    }

    /**
     * 匹配用户标识审计字段
     * @param field       属性包装对象
     * @param matching    审计模式
     * @param autoDiscern 是否自动匹配
     * @param annotation  注解类
     * @return true: 是 false: 否
     */
    public static boolean matchAuditForUser(FieldWrapper field, AuditMatching matching,
                                            boolean autoDiscern, Class<? extends Annotation> annotation) {
        return matchAudit(field, AuditType.USER_ID, matching, autoDiscern, annotation);
    }

    /**
     * 匹配用户名审计字段
     * @param field       属性包装对象
     * @param matching    审计模式
     * @param autoDiscern 是否自动匹配
     * @param annotation  注解类
     * @return true: 是 false: 否
     */
    public static boolean matchAuditForUserName(FieldWrapper field, AuditMatching matching,
                                                boolean autoDiscern, Class<? extends Annotation> annotation) {
        return matchAudit(field, AuditType.USER_NAME, matching, autoDiscern, annotation);
    }
}
