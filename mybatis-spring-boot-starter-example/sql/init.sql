DROP TABLE IF EXISTS GRADE;
CREATE TABLE GRADE
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    NAME                    VARCHAR(50) COMMENT '年级名称',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间'
) COMMENT '年级信息表';

DROP TABLE IF EXISTS TEACHER;
CREATE TABLE TEACHER
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    NAME                    VARCHAR(30) COMMENT '姓名',
    SEX                     INT(1) COMMENT '性别',
    GRADE_ID                BIGINT(22) COMMENT '所属年级',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间'
) COMMENT '老师信息表';

DROP TABLE IF EXISTS STUDENT;
CREATE TABLE STUDENT
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    NAME                    VARCHAR(100) COMMENT '学生姓名',
    PASSWORD                VARCHAR(255) COMMENT '登录密码',
    SEX                     INT(1) COMMENT '性别',
    PHONE                   VARCHAR(30) COMMENT '联系号码',
    ADDRESS                 VARCHAR(255) COMMENT '联系地址',
    BIRTHDAY                DATETIME COMMENT '出生日期',
    EMAIL                   VARCHAR(50) COMMENT '电子邮箱',
    GRADE_ID                BIGINT(22) COMMENT '所在年级',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间'
) COMMENT '学生信息表';

DROP TABLE IF EXISTS SUBJECT;
CREATE TABLE SUBJECT
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    NAME                    VARCHAR(100) COMMENT '科目名称',
    HOURS                   INT(5) COMMENT '课程学时',
    GRADE_ID                BIGINT(22) COMMENT '课程所属年级',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间'
) COMMENT '科目信息表';

DROP TABLE IF EXISTS RESULT;
CREATE TABLE RESULT
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    STUDENT_ID              BIGINT(22) COMMENT '所属学生',
    SUBJECT_ID              BIGINT(22) COMMENT '所属科目',
    SCORE                   INT(3) COMMENT '分数',
    EXAM_DATE               DATETIME COMMENT '考试日期',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间'
) COMMENT '学生成绩信息表';

DROP TABLE IF EXISTS SYS_USER;
CREATE TABLE SYS_USER
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    USER_NAME               VARCHAR(30) COMMENT '用户名',
    PASSWORD                VARCHAR(255) COMMENT '密码',
    STATE                   INT(1) COMMENT '状态',
    SEX                     INT(1) COMMENT '性别',
    SCORE                   INT(3) COMMENT '分数',
    VERSION                 INT(9) COMMENT '版本',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间',
    DELETED_USER_NAME       VARCHAR(30) COMMENT '删除人名称',
    DELETED_USER_ID         BIGINT(22) COMMENT '删除人ID',
    GMT_DELETED             DATETIME COMMENT '删除时间'
) COMMENT '用户信息表';
