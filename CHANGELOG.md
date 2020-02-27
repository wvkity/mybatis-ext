# CHANGELOG
## [v1.0.1] 2020-02-06
- 移除自动填充值相关类(MetaFilling、MetaObjectFillingInterceptor、MetaObjectFillingExecutor等)
- 新增元数据审计类(data.auditing包下都是审计相关)替代自动填充值类，优化自动填充值功能
- 主键、逻辑删除自动填充值功能已分离到其他的拦截器实现
- 雪花算法主键Bean自动注册
- MapperExecutor更名为MapperExecutorCallable，UnifyMapperExecutor更名为MapperExecutor，ServiceExecutor更名为ServiceExecutorCallable，
UnifyServiceExecutor更名为ServiceExecutor
- 优化自定义拦截器功能
- 更改Mapper、Service部分方法名
- 优化乐观锁拦截器
- 修复存在的bug
- 添加批量保存功能支持simple、batch模式
- 优化分页拦截器(删除旧的拦截器)
- 修复实体类解析中自动映射jdbcType存在的NPE异常
- 修复逻辑删除存在jdbcType映射错误bug
- 优化Mybatis插件注入，删除AbstractBeanDefinitionRegistry、Plugin(枚举)等类
- meta包名更名为metadata
- 简化全局配置主键类型
- 废除旧的实体解析器、属性解析器、表映射类、字段类、属性类，新增对应的类，优化实体解析
- 查询条件类统一移至criteria包下
- 修复SQL映射写死换行符bug
- criteria查询条件对象添加默认别名
- 修复连接子查询时使用子查询别名bug
- LambdaResolver更名为LambdaConverter
- InsertService更名为SaveService
- 修复service层批量保存操作事务没交由spring托管
- 重写BatchExecutor、SimpleExecutor、ReuseExecutor
- 雪花算法开始时间戳调整至2020-01-25 00:00:00
- 修复其他bug

## [v1.0.2] 2020-02-07
- ForeignCriteria类中的appendTo方法更名为join
- SqlSessionUtil类从mybatis-code模块迁移到mybatis-extension模块
- 修复Criteria聚合函数条件指定小数位数无效bug
