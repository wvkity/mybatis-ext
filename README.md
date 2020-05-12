# mybatis扩展框架

## 介绍
<p align="left">
  欢迎使用mybatias扩展框架，为简化开发工作、提高生产率而生，提供更丰富的API
</p>
<p align="left">
  <a href="https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.wvkity">
    <img alt="Maven Central" src="https://img.shields.io/maven-central/v/com.wvkity/mybatis-core">
  </a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

## 使用示例
> 请访问项目(github): [mybatis-spring-example](https://github.com/wvkity/mybatis-spring-example)

> 请访问项目(gitee): [mybatis-spring-example](https://gitee.com/wvkity/mybatis-spring-example)

## MyBatis-Ext是什么?
* MyBatis-Ext是在原生MyBatis的基础上做增强扩展，不影响任何原生的使用，尽可能快速方便集成MyBatis。
* MyBatis-Ext提供了`单表基础数据`的`CURD`操作以及`批量保存数据`的操作，除此之外也支持联表查询、子查询、子查询联表查询等查询方式，尽可能提供更全面的API。

## 支持的数据库
* mysql、mariadb、oracle、sqlserver、db2、h2、hsql、sqlite 、 postgresql

## 使用环境
`SpringBoot`的发展趋势已经势如破竹，这里只做`SpringBoot`集成展示，当然也支持`Spring MVC`
* JDK1.8以上版本
* SpringBoot2.X以上版本(1.X暂未测试)

## 安装
安装相对比较简单，项目添加对应依赖即可。
* 使用`Maven`构建项目方式
  ~~~maven
    <dependency>
        <groupId>com.wvkity</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${lastVersion}</version>
    </dependency>
  ~~~
* 使用`Gradle`构建项目方式
  ~~~gradle
    implementation 'com.wvkity:mybatis-spring-boot-starter:$lastVersion'
  ~~~
## 核心功能
### 代码生成器
**目前还在开发中，敬请期待...**

### CURD接口

#### Mapper CURD接口
> **说明**
> * `BaseMapper<T, V, PK>`作为Mapper通用CURD的基础接口
> * `UniformMapper<T, PK>`继承于`BaseMapper<T, V, PK>`接口，表示实体对象、返回值都是同一类型
> * `SerialMapper<T, V>`继承于`BaseMapper<T, V, PK>`接口，表示主键类型为`Serializable`，实体对象、返回值不为同一类型
> * `SimpleMapper<T>`继承于`SerialMapper<T, V>`接口，表示主键类型为`Serializable`，实体对象、返回值为同一类型
> * 泛型`T`为任意实体对象
> * 泛型`V`可以为实体对象、VO对象、DTO对象等，作为查询返回值
> * 泛型`PK`可以为`Serializable`、`Integer`、`Long`等主键类型
>  * `Criteria`为条件包装容器基础接口，其实现类包含`SimpleCriteria`, `QueryCriteria`, `UpdateCriteria`, `CriteriaImpl`, `SubCriteria`, `ForeignCriteria`, `ForeignSubCriteria`，用于实现单表查询更新删除、联表查询功能
> * 目前暂不支持复合主键
> * 根据自身业务需求，继承对应接口即可

##### Insert
~~~java
// 插入一条记录(元数据审计插件开启时，会自动审计对象)
int insert(@Param(Constants.PARAM_ENTITY) T entity);

// 插入一条记录(过滤空值字段)
int insertNotWithNull(@Param(Constants.PARAM_ENTITY) T entity);

// 批量插入数据(元数据审计插件开启时，会自动审计对象)
int batchInsert(@Param(Constants.PARAM_BATCH_BEAN_WRAPPER) BatchDataBeanWrapper<T> wrapper);

// 批量插入数据(不会自动审计对象)
int batchInsertNotWithAudit(@Param(Constants.PARAM_BATCH_BEAN_WRAPPER) BatchDataBeanWrapper<T> wrapper);
~~~

**参数说明**

| 类型 | 参数名 | 描述 |
| :--: | :----: | :--: |
|   T   |    entity    |   实体对象   |
|   BatchDataBeanWrapper<T>   |     wrapper   |   实体列表包装对象   |

------

##### Delete

~~~java
// 根据主键删除记录
int deleteById(PK id);

// 根据主键列表批量删除记录
int batchDeleteById(@Param(Constants.PARAM_PRIMARY_KEYS) List<PK> idList);

// 根据实体对象删除记录
int delete(@Param(Constants.PARAM_ENTITY) T entity);

// 根据实体对象列表批量删除记录
int batchDelete(@Param(Constants.PARAM_ENTITIES) Collection<T> entities);

// 根据条件包装对象删除记录
int deleteByCriteria(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

// 根据实体对象逻辑删除记录
int logicDelete(@Param(Constants.PARAM_ENTITY) T entity);

// 根据条件包装对象逻辑删除记录
int logicDeleteByCriteria(@Param(Constants.PARAM_CRITERIA) final Criteria<T> criteria);
~~~

**参数说明**

|      类型      | 参数名称 |     描述     |
| :------------: | :------: | :----------: |
|       T        |  entity  |   实体对象   |
| Collection<T>  | entities | 实体对象列表 |
|       PK       |    id    |    主键ID    |
| Collection<PK> |  idList  |   主键列表   |
|  Criteria<T>   | criteria | 条件包装对象 |

------

##### Update

~~~java
// 根据实体对象更新记录
int update(@Param(Constants.PARAM_ENTITY) T entity);

// 根据实体对象更新记录(排除空值字段)
int updateNotWithNull(@Param(Constants.PARAM_ENTITY) T entity);

// 根据实体对象更新记录(排除乐观锁条件)
int updateNotWithLocking(@Param(Constants.PARAM_ENTITY) T entity);

// 根据实体对象更新记录(排除空值字段及乐观锁条件)
int updateNotWithNullAndLocking(@Param(Constants.PARAM_ENTITY) T entity);

// 根据条件包装对象更新记录(此处的`Criteria`对象只能为`UpdateCriteria`或`CriteriaImpl`)
int updateByCriteria(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

// 根据实体对象(值)、条件包装对象(条件)更新记录
int mixinUpdateNotWithNull(@Param(Constants.PARAM_ENTITY) T entity,
                           @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);
~~~

**参数说明**

|    类型     |  参数名  |     描述     |
| :---------: | :------: | :----------: |
|      T      |  entity  |   实体对象   |
| Criteria<T> | criteria | 条件包装对象 |

------

##### Select

~~~java
~~~

**参数说明**

------

