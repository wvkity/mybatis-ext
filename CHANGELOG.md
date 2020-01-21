# CHANGELOG
## [v1.0.1] 2020-01-21
- 移除自动填充值相关类(MetaFilling、MetaObjectFillingInterceptor、MetaObjectFillingExecutor等)
- 新增元数据审计类(data.auditing包下都是审计相关)替代自动填充值类，优化自动填充值功能
- 主键、逻辑删除自动填充值功能已分离到其他的拦截器实现
- 雪花算法主键Bean自动注册
- MapperExecutor更名为MapperExecutorCallable，UnifyMapperExecutor更名为MapperExecutor，ServiceExecutor更名为ServiceExecutorCallable，
UnifyServiceExecutor更名为ServiceExecutor
- 优化自定义拦截器功能
- 修复存在bug

