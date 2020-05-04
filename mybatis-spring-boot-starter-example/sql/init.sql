
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
CREATE DATABASE IF NOT EXISTS MY_SCHOOL DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
USE MY_SCHOOL;
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
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间',
    DELETED_USER_ID         BIGINT(22) COMMENT '删除人ID',
    DELETED_USER_NAME       VARCHAR(30) COMMENT '删除人名称',
    GMT_DELETED             DATETIME COMMENT '删除时间'
) COMMENT '年级信息表';

DROP TABLE IF EXISTS KLASS;
CREATE TABLE KLASS
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    NAME                    VARCHAR(50) COMMENT '班级名称',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间',
    DELETED_USER_ID         BIGINT(22) COMMENT '删除人ID',
    DELETED_USER_NAME       VARCHAR(30) COMMENT '删除人名称',
    GMT_DELETED             DATETIME COMMENT '删除时间'
) COMMENT '班级级信息表';

DROP TABLE IF EXISTS TEACHER;
CREATE TABLE TEACHER
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    NAME                    VARCHAR(30) COMMENT '姓名',
    SEX                     INT(1) COMMENT '性别[0: 女, 1: 男]',
    PHONE                   VARCHAR(30) COMMENT '联系号码',
    REMARK                  VARCHAR(50) COMMENT '备注',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间',
    DELETED_USER_ID         BIGINT(22) COMMENT '删除人ID',
    DELETED_USER_NAME       VARCHAR(30) COMMENT '删除人名称',
    GMT_DELETED             DATETIME COMMENT '删除时间'
) COMMENT '教师信息表';

DROP TABLE IF EXISTS STUDENT;
CREATE TABLE STUDENT
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    NAME                    VARCHAR(100) COMMENT '学生姓名',
    SEX                     INT(1) COMMENT '性别[0: 女, 1: 男]',
    PHONE                   VARCHAR(30) COMMENT '联系号码',
    ADDRESS                 VARCHAR(255) COMMENT '联系地址',
    BIRTHDAY                DATETIME COMMENT '出生日期',
    EMAIL                   VARCHAR(50) COMMENT '电子邮箱',
    KLASS_ID                BIGINT(22) COMMENT '所在班级',
    PERIOD                  INT(5) COMMENT '学届',
    VERSION                 INT(9) COMMENT '版本',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间',
    DELETED_USER_ID         BIGINT(22) COMMENT '删除人ID',
    DELETED_USER_NAME       VARCHAR(30) COMMENT '删除人名称',
    GMT_DELETED             DATETIME COMMENT '删除时间'
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
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间',
    DELETED_USER_ID         BIGINT(22) COMMENT '删除人ID',
    DELETED_USER_NAME       VARCHAR(30) COMMENT '删除人名称',
    GMT_DELETED             DATETIME COMMENT '删除时间'
) COMMENT '科目信息表';

DROP TABLE IF EXISTS RELEVANCE;
CREATE TABLE RELEVANCE(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    TEACHER_ID              BIGINT(22) COMMENT '教师编号',
    KLASS_ID                BIGINT(22) COMMENT '班级编号',
    SUBJECT_ID              BIGINT(22) COMMENT '科目编号',
    PERIOD                  INT(5) COMMENT '学届',
    YEAR                    INT(5) COMMENT '年份',
    SEMESTER                INT(1) COMMENT '学期[1: 第一学期, 2: 第二学期]'
) COMMENT '教师-班级-课程关系表';

DROP TABLE IF EXISTS EXAM;
CREATE TABLE EXAM
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    STUDENT_ID              BIGINT(22) COMMENT '所属学生',
    EXAM_CODE               VARCHAR(100) COMMENT '考试代号',
    SUBJECT_ID              BIGINT(22) COMMENT '所属科目',
    SCORE                   INT(3) COMMENT '分数',
    EXAM_YEAR               INT(5) COMMENT '考试年份',
    PERIOD                  INT(5) COMMENT '学届',
    GRADE                   INT(1) COMMENT '年级[1: 初一年级, 2: 初二年级, 3: 初三年级]',
    SEMESTER                INT(1) COMMENT '学期[1: 第一学期, 2: 第二学期]',
    EXAM_DATE               DATETIME COMMENT '考试日期',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间',
    DELETED_USER_ID         BIGINT(22) COMMENT '删除人ID',
    DELETED_USER_NAME       VARCHAR(30) COMMENT '删除人名称',
    GMT_DELETED             DATETIME COMMENT '删除时间'
) COMMENT '学生成绩信息表';

DROP TABLE IF EXISTS SYS_USER;
CREATE TABLE SYS_USER
(
    ID                      BIGINT(22) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    USER_NAME               VARCHAR(30) COMMENT '用户名',
    PASSWORD                VARCHAR(255) COMMENT '密码',
    STATE                   INT(1) COMMENT '状态',
    SEX                     INT(1) COMMENT '性别',
    PHONE                   VARCHAR(30) COMMENT '电话号码',
    VERSION                 INT(9) COMMENT '版本',
    IS_DELETED              INT(1) COMMENT '删除标识[0: 未删除, 1: 已删除]',
    CREATED_USER_NAME       VARCHAR(30) COMMENT '创建人',
    CREATED_USER_ID         BIGINT(22) COMMENT '创建人ID',
    GMT_CREATED             DATETIME COMMENT '创建时间',
    LAST_MODIFIED_USER_NAME VARCHAR(30) COMMENT '最后修改人',
    LAST_MODIFIED_USER_ID   BIGINT(22) COMMENT '最后修改人ID',
    GMT_LAST_MODIFIED       DATETIME COMMENT '最后修改时间',
    DELETED_USER_ID         BIGINT(22) COMMENT '删除人ID',
    DELETED_USER_NAME       VARCHAR(30) COMMENT '删除人名称',
    GMT_DELETED             DATETIME COMMENT '删除时间'
) COMMENT '用户信息表';
