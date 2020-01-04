package com.wkit.lost.mybatis.generator.bean


class GeneratorConfig {

    /**
     * 保存的名称
     */
    var name: String? = null
    /**
     * 项目目录
     */
    var projectFolder: String? = null
    /**
     * 模块(项目)名称
     */
    var moduleName: String? = null
    /**
     * groupId(Maven|Gradle)
     */
    var uniqueGroupId: String? = null
    /**
     * 表前缀
     */
    var tableNamePrefix: String? = null
    /**
     * 主键(ID)
     */
    var primaryKey: String? = null

    /**
     * 主键类型
     */
    var needPrimaryKeyType: String? = null

    /**
     * 源码目录
     */
     var sourceCodeTargetFolder: String? = null

    /**
     * 测试源码目录
     */
    var testCodeTargetFolder: String? = null

    /**
     * 配置文件资源目录
     */
    var resourcesTargetFolder: String? = null

    /**
     * 根包名
     */
    var rootTargetPackage: String? = null

    /**
     * 自定义Dao通用接口
     */
    var baseDaoInterface: String? = null

    /**
     * 自定义Service通用接口
     */
    var baseServiceInterface: String? = null

    /**
     * 自定义ServiceImpl通用接口
     */
    var baseServiceImpl: String? = null

    /**
     * 实体类名
     */
    var entityClassName: String? = null

    /**
     * 实体类包名
     */
    var entityTargetPackage: String? = null

    /**
     * DTO类名
     */
    var dtoEntityClassName: String? = null

    /**
     * DTO类包名
     */
    var dtoEntityTargetPackage: String? = null

    /**
     * Mapper接口名
     */
    var mapperClassName: String? = null

    /**
     * Mapper接口包名
     */
    var mapperTargetPackage: String? = null

    /**
     * 映射XML文件名
     */
    var mappingFileName: String? = null

    /**
     * 映射XML文件目录
     */
    var mappingTargetFolder: String? = null

    /**
     * Service接口名
     */
    var serviceClassName: String? = null

    /**
     * Service接口包名
     */
    var serviceTargetPackage: String? = null

    /**
     * ServiceImpl名
     */
    var serviceImplClassName: String? = null

    /**
     * ServiceImpl包名
     */
    var serviceImplTargetPackage: String? = null

    /**
     * Controller类名
     */
    var controllerClassName: String? = null

    /**
     * Controller包名
     */
    var controllerTargetPackage: String? = null

    /**
     * Test类包名
     */
    var testTargetPackage: String? = null

    /**
     * Test类名
     */
    var testClassName: String? = null

    /**
     * JPA注解
     */
    var jpaAnnotationChoice: String? = "内置"

    /**
     * 文件编码格式
     */
    var fileEncodingChoice: String? = "UTF-8"

    /**
     * 生成DTO类
     */
    var needDtoEntity: Boolean = true

    /**
     * 生成Dao接口
     */
    var needDaoInterface: Boolean = true

    /**
     * 生成Service接口
     */
    var needServiceInterface: Boolean = true

    /**
     * 生成Controller类
     */
    var needController: Boolean = true

    /**
     * 实现序列化接口
     */
    var entityImplSerializable: Boolean = true

    /**
     * DTO类实现序列化接口
     */
    var dtoImplSerializable: Boolean = true

    /**
     * 使用Schema前缀
     */
    var useSchemaPrefix: Boolean = false

    /**
     * 表名前缀匹配时，类名自动去除前缀
     */
    var replaceWithTablePrefix: Boolean = true

    /**
     * 覆盖原XML文件
     */
    var overrideXml: Boolean = true

    /**
     * 生成实体类注释
     */
    var needComment: Boolean = true

    /**
     * 使用Lombok插件
     */
    var useLombokPlugin: Boolean = true

    /**
     * 生成toString、hashCode、equals方法
     */
    var needToStringHashCodeEquals: Boolean = true

    /**
     * Mapper接口使用@Repository注解
     */
    var useRepositoryAnnotation: Boolean = true

    /**
     * 使用DTO类作为返回值
     */
    var useDtoAsReturnValue: Boolean = true
    
    /**
     * 类属性映射自动移除IS前缀
     */
    var propertyMappingRemoveIsPrefix: Boolean = true

    /**
     * 实体类使用JPA注解
     */
    var entityUseJpaAnnotation: Boolean = true

    /**
     * 实体类使用swagger注解
     */
    var entityUseSwaggerAnnotation: Boolean = false

    /**
     * DTO类使用swagger注解
     */
    var dtoUseSwaggerAnnotation: Boolean = true
}
