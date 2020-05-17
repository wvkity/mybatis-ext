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
// 插入一条记录(会自动审计对象)
int insert(@Param(Constants.PARAM_ENTITY) T entity);

// 插入一条记录(过滤空值字段)
int insertNotWithNull(@Param(Constants.PARAM_ENTITY) T entity);

// 批量插入数据(会自动审计对象)
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
// 根据主键检查记录是否存在
int existsById(PK id);

// 根据指定实体对象检查记录是否存在
int exists(@Param(Constants.PARAM_ENTITY) T entity);

// 根据条件包装对象查询记录是否存在
int existsByCriteria(@Param(Constants.PARAM_CRITERIA) final Criteria<T> criteria);

// 根据指定实体对象查询记录数
long count(@Param(Constants.PARAM_ENTITY) T entity);

// 根据条件包装对象查询记录数
long countByCriteria(@Param(Constants.PARAM_CRITERIA) final Criteria<T> criteria);

// 根据主键查询记录
Optional<V> selectOne(PK id);

// 查询所有记录
List<V> allList();

// 根据多个主键查询记录
List<V> list(@Param(Constants.PARAM_PRIMARY_KEYS) Collection<PK> idList);

// 根据指定对象查询记录
List<V> listByEntity(@Param(Constants.PARAM_ENTITY) T entity);

// 根据多个对象查询记录
List<V> listByEntities(@Param(Constants.PARAM_ENTITIES) Collection<T> entities);

// 根据条件包装对象查询记录
List<V> listByCriteria(@Param(Constants.PARAM_CRITERIA) final Criteria<T> criteria);

// 简单分页查询记录
List<V> simpleList(@Param(Constants.PARAM_PAGEABLE) Pageable pageable);

// 根据实体对象分页查询记录
List<V> pageableList(@Param(Constants.PARAM_ENTITY) T entity,
                     @Param(Constants.PARAM_PAGEABLE) Pageable pageable);

// 根据条件包装对象分页查询记录
List<V> pageableListByCriteria(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria,@Param(Constants.PARAM_PAGEABLE) Pageable pageable);

// ----- 特殊方法 -----
// 查询记录(自定义Object返回值)
List<Object> objectList(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

// 查询记录(自定义Object数组返回值)
List<Object[]> arrayList(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

// 查询记录(自定义Map返回值)
List<Map<String, Object>> mapList(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

// 分页查询记录(自定义Object返回值)
List<Object> objectPageableList(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria,@Param(Constants.PARAM_PAGEABLE) Pageable pageable);

// 分页查询记录(自定义Object数组返回值)
List<Object[]> arrayPageableList(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria, @Param(Constants.PARAM_PAGEABLE) Pageable pageable);

// 分页查询记录(自定义返回Map返回值)
List<Map<String, Object>> mapPageableList(@Param(Constants.PARAM_CRITERIA) Criteria<T> criteria, @Param(Constants.PARAM_PAGEABLE) Pageable pageable);

~~~

**参数说明**

|    类型     |  参数名  |     描述     |
| :---------: | :------: | :----------: |
|     PK      |    id    |     主键     |
|      T      |  entity  |   实体对象   |
|  Pageable   | pageable |   分页对象   |
| Criteria<T> | criteria | 条件包装对象 |

------



#### Service CRUD接口

**说明**

> * `BaseService<T, V, PK>`作为Mapper通用CURD的基础接口
> * `UniformService<T, PK>`继承于`BaseService<T, V, PK>`接口，表示实体对象、返回值都是同一类型
> * `SerialService<T, V>`继承于`BaseService<T, V, PK>`接口，表示主键类型为`Serializable`，实体对象、返回值不为同一类型
> * `SimpleService<T>`继承于`SerialService<T, V>`接口，表示主键类型为`Serializable`，实体对象、返回值为同一类型
> * 泛型`T`为任意实体对象
> * 泛型`V`可以为实体对象、VO对象、DTO对象等，作为查询返回值
> * 泛型`PK`可以为`Serializable`、`Integer`、`Long`等主键类型
>  * `Criteria`为条件包装容器基础接口

##### Save

~~~java
// 保存记录
int save(final T entity);

// 保存记录(过滤空值字段)
int saveNotWithNull(final T entity);

// 批量保存记录(自动审计元数据)
int batchSave(T... entities);

// 批量保存记录(自动审计元数据)
int batchSave(final Collection<T> entities);

// 批量保存记录(自动审计元数据) 
int batchSave(final Collection<T> entities, int batchSize);

// 批量保存记录(自动审计元数据)
int batchSave(final BatchDataBeanWrapper<T> wrapper);

// 批量保存记录(不自动审计元数据)
int batchSaveNotWithAudit(T... entities);

// 批量保存记录(不自动审计元数据)
int batchSaveNotWithAudit(final Collection<T> entities);

// 批量保存记录(不自动审计元数据)
int batchSaveNotWithAudit(final Collection<T> entities, int batchSize);

// 批量保存记录(不自动审计元数据)
int batchSaveNotWithAudit(final BatchDataBeanWrapper<T> wrapper);

// 批量保存记录
int embeddedBatchSave(T... entities);

// 批量保存记录
int embeddedBatchSave(Collection<T> entities);

// 批量保存记录
int embeddedBatchSave(Collection<T> entities, int batchSize);

// 批量保存记录
int embeddedBatchSaveNotWithNull(T... entities);

// 批量保存记录
int embeddedBatchSaveNotWithNull(Collection<T> entities);

// 批量保存记录
int embeddedBatchSaveNotWithNull(Collection<T> entities, int batchSize);

~~~

**参数说明**

| 类型 | 参数名 | 描述 |
| :--: | :----: | :--: |
|   T   |    entity    |   实体对象   |
|   Collection<T>   | entities   |   实体列表   |
| BatchDataBeanWrapper<T> | wrapper | 实体列表包装对象 |
| int | batchSize | 批处理大小 |

**注意事项**

> * `batchSave`或`batchSaveNotWithAudit`是采用`MyBatis`插件方式实现的，需要开启`BatchParameterFilterInterceptor`、`BatchStatementInterceptor`插件
> * `embeddedBatchSave`或`embeddedBatchSaveNotWithNull`针对的是`BATCH`模式，如果当前为`SIMPLE`模式且没有事务或者当前的批量操作是在其他的增、删、改操作之前执行则不会存在问题，否则会出现`NPE`异常

------

##### Delete

~~~java
// 根据主键删除记录
int deleteById(PK id);

// 根据指定实体对象删除记录
int delete(T entity);

// 根据条件包装对象删除记录
int delete(Criteria<T> criteria);

// 根据主键批量删除记录
int batchDeleteById(PK... ids);

// 根据主键批量删除记录
int batchDeleteById(Collection<PK> ids);

// 根据实体对象批量删除记录
int batchDelete(T... entities);

// 根据实体对象批量删除记录
int batchDelete(List<T> entities);

~~~

**参数说明**

|      类型      | 参数名称 |     描述     |
| :------------: | :------: | :----------: |
|       T        |  entity  |   实体对象   |
|    List<T>     | entities | 实体对象列表 |
|       PK       |    id    |    主键ID    |
| Collection<PK> |  idList  |   主键列表   |
|  Criteria<T>   | criteria | 条件包装对象 |

-----

##### LogicDelete

~~~java
// 根据实体对象逻辑删除记录
int logicDelete(T entity);

// 根据条件包装对象逻辑删除记录
int logicDelete(Criteria<T> criteria);

~~~

**参数说明**

|    类型     | 参数名称 |     描述     |
| :---------: | :------: | :----------: |
|      T      |  entity  |   实体对象   |
| Criteria<T> | criteria | 条件包装对象 |

-----

##### Update

~~~java
// 根据实体对象更新记录(自动审计，ID、乐观锁为条件)
int update(final T entity);

// 根据实体对象更新记录(自动审计，过滤空值字段，ID、乐观锁为条件)
int updateNotWithNull(final T entity);

// 根据实体对象更新记录(自动审计，ID为条件)
int updateNotWithLocking(final T entity);

// 根据实体对象更新记录(不自动审计，ID为条件)
int updateNotWithNullAndLocking(final T entity);

// 根据条件包装对象更新记录
int update(Criteria<T> criteria);

// 根据实体对象、条件包装对象更新记录(实体对象为更新数据，条件包装对象为条件)
int updateNotWithNull(T entity, Criteria<T> criteria);

~~~

**参数说明**

|    类型     | 参数名称 |     描述     |
| :---------: | :------: | :----------: |
|      T      |  entity  |   实体对象   |
| Criteria<T> | criteria | 条件包装对象 |

-----

##### Exists

~~~java
// 根据主键检查记录是否存在
boolean existsById(PK id);

// 根据实体对象检查记录是否存在
boolean exists(T entity);

// 根据条件包装对象检查记录是否存在
boolean exists(Criteria<T> criteria);

~~~

**参数说明**

|    类型     | 参数名称 |     描述     |
| :---------: | :------: | :----------: |
|      T      |  entity  |   实体对象   |
| Criteria<T> | criteria | 条件包装对象 |

-----

##### Count

~~~java
// 查询记录总数
long count();

// 根据实体对象查询记录总数
long count(T entity);

// 根据条件对象查询记录总数
long count(Criteria<T> criteria);

~~~

**参数说明**

|    类型     | 参数名称 |     描述     |
| :---------: | :------: | :----------: |
|      T      |  entity  |   实体对象   |
| Criteria<T> | criteria | 条件包装对象 |

-----

##### Get

~~~java
// 根据主键查询记录
Optional<V> selectOne(PK id);

~~~

**参数说明**

| 类型 | 参数名称 | 描述 |
| :--: | :------: | :--: |
|  PK  |    id    | 主键 |

-----

##### List

~~~java
// 查询所有记录
List<V> list();

// 根据多个主键查询记录
List<V> list(PK... ids);

// 根据多个主键查询记录
List<V> list(Collection<PK> ids);

// 根据实体对象查询记录
List<V> list(T entity);

// 根据多个实体对象查询记录
List<V> listByEntities(T... entities);

// 根据多个实体对象查询记录
List<V> list(List<T> entities);

// 根据条件对包装象查询记录
List<V> list(Criteria<T> criteria);

// 根据条件对象查询记录
List<Object> objectList(Criteria<T> criteria);

// 根据条件包装对象查询记录
List<Object[]> arrayList(Criteria<T> criteria);

// 根据条件包装对象查询记录
List<Map<String, Object>> mapList(Criteria<T> criteria);

~~~

**参数说明**

|      类型      | 参数名称 |     描述     |
| :------------: | :------: | :----------: |
|      T...      | entities | 实体对象数组 |
|    List<T>     | entities | 实体对象列表 |
|     PK...      |   ids    |   主键数组   |
| Collection<PK> |   ids    |   主键列表   |
|  Criteria<T>   | criteria | 条件包装对象 |

-----

##### PageableList

~~~java
// 分页查询记录
List<V> list(Pageable pageable);

// 根据指定实体对象分页查询记录
List<V> list(T entity, Pageable pageable);

// 根据条件包装对象分页查询记录
List<V> list(Criteria<T> criteria, Pageable pageable);

// 根据条件包装对象分页查询记录
List<Object> objectList(Criteria<T> criteria, Pageable pageable);

// 根据条件包装对象分页查询记录
List<Object[]> arrayList(Criteria<T> criteria, Pageable pageable);

// 根据条件包装对象分页查询记录
List<Map<String, Object>> mapList(Criteria<T> criteria, Pageable pageable);

~~~

**参数说明**

|    类型     | 参数名称 |     描述     |
| :---------: | :------: | :----------: |
|      T      |  entity  |   实体对象   |
| Criteria<T> | criteria | 条件包装对象 |
|  Pageable   | pageable |   分页对象   |

-----

##### Customize

~~~Java
// 根据条件包装对象查询记录
<E> List<E> customize(Criteria<T> criteria);

// 根据条件包装对象分页查询记录
<E> List<E> customize(Criteria<T> criteria, Pageable pageable);

~~~

**参数说明**

|    类型     | 参数名称 |     描述     |
| :---------: | :------: | :----------: |
| Criteria<T> | criteria | 条件包装对象 |
|  Pageable   | pageable |   分页对象   |

**注意事项**

> * 条件包装对象`Criteria`必须指定`resultType`或`resultMap`值，否则返回空集合

------

#### CURD接口使用

##### 实体类

* **Grade**

> ~~~java
> /**
>  * 年级
>  */
> @Data
> @ToString
> @EqualsAndHashCode
> @NoArgsConstructor
> @AllArgsConstructor
> @FieldNameConstants
> @Accessors(chain = true)
> public class Grade {
> 
>     private static final long serialVersionUID = -4653601420462226184L;
> 
>     /**
>      * 主键
>      */
>     private Long id;
> 
>     /**
>      * 年级名称
>      */
>     private String name;
> 
>     /**
>      * 删除标识
>      */
>     private Boolean deleted;
> 
>     /**
>      * 创建人
>      */
>     private String createdUserName;
> 
>     /**
>      * 创建人ID
>      */
>     private Long createdUserId;
> 
>     /**
>      * 创建时间
>      */
>     private LocalDateTime gmtCreated;
> 
>     /**
>      * 最后更新人
>      */
>     private String lastModifiedUserName;
> 
>     /**
>      * 最后更新人ID
>      */
>     private Long lastModifiedUserId;
> 
>     /**
>      * 最后更新时间
>      */
>     private LocalDateTime gmtLastModified;
> 
>     /**
>      * 删除人ID
>      */
>     private Long deletedUserId;
>     
>     /**
>      * 删除人名称
>      */
>     private String deletedUserName;
> 
>     /**
>      * 删除时间
>      */
>     private LocalDateTime gmtDeleted;
> }
> ~~~

* **GradeVo**

> ~~~java
> @Data
> @EqualsAndHashCode
> @ToString
> @Accessors(chain = true)
> @NoArgsConstructor
> @AllArgsConstructor
> public class GradeVo implements Serializable {
> 
>     private static final long serialVersionUID = 446838895774432290L;
>     /**
>      * 年级编号
>      */
>     private Long id;
> 
>     /**
>      * 年级名称
>      */
>     private String name;
> 
>     /**
>      * 删除标识
>      */
>     private Boolean deleted;
> 
>     /**
>      * 创建时间
>      */
>     private LocalDateTime gmtCreated;
> }
> ~~~

-----

##### 继承`BaseMapper`方式

* **GradeMapper**

> ~~~java
> @Repository
> public interface GradeMapper extends BaseMapper<Grade, GradeVo, Long> {
> }
> ~~~

* **GradeService**

> ~~~java
> public interface GradeService extends BaseService<Grade, GradeVo, Long> {
> }
> ~~~

* **GradeServiceImpl**

> ~~~java
> @Service
> public class DefaultGradeService extends AbstractBaseService<GradeMapper, Grade, GradeVo, Long> implements GradeService {
> }
> ~~~

-----

##### 继承`UniformMapper`方式

* **GradeMapper**

> ~~~java
> @Repository
> public interface GradeMapper extends UniformMapper<Grade, Long> {
> }
> ~~~

* **GradeService**

> ~~~java
> public interface GradeService extends UniformService<Grade, Long> {
> }
> ~~~

* **GradeServiceImpl**

> ~~~java
> @Service
> public class DefaultGradeService extends AbstractUniformService<GradeMapper, Grade, Long> implements GradeService {
> }
> ~~~

-----

##### 继承`SerialMapper`方式

* **GradeMapper**

> ~~~java
> @Repository
> public interface GradeMapper extends SerialMapper<Grade, GradeVo> {
> }
> ~~~

* **GradeService**

> ~~~java
> public interface GradeService extends SerialService<Grade, GradeVo> {
> }
> ~~~

* **GradeServiceImpl**

> ~~~java
> @Service
> public class DefaultGradeService extends AbstractSerialService<GradeMapper, Grade, GradeVo> implements GradeService {
> }
> ~~~

-----

##### 继承`SimpleMapper`方式

* **GradeMapper**

> ~~~java
> @Repository
> public interface GradeMapper extends SimpleMapper<Grade> {
> }
> ~~~

* **GradeService**

> ~~~java
> public interface GradeService extends SimpleService<Grade> {
> }
> ~~~

* **GradeServiceImpl**

> ~~~java
> @Service
> public class DefaultGradeService extends AbstractSimpleService<GradeMapper, Grade> implements GradeService {
> }
> ~~~

-----

### 条件包装器

#### 说明

