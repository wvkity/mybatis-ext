package com.wkit.lost.mybatis.generator.bean

import com.wkit.lost.mybatis.generator.utils.FileUtil


class GeneratorConfig {

    companion object {
        private const val EMPTY = ""
        private const val MODULE_NAME = "{ModuleName}"
        private const val BASE_PACKAGE = "{BasePackage}"
        private val DOT_REGEX = Regex("\\.")
    }
    
    private var basePackageTempVariable = ""

    /**
     * 保存的名称
     */
    var name: String = ""
    /**
     * 项目目录
     */
    var projectFolder: String = ""
    /**
     * 模块(项目)名称
     */
    var moduleName: String = ""
    /**
     * groupId(Maven|Gradle)
     */
    var uniqueGroupId: String = ""
    /**
     * 表前缀
     */
    var tableNamePrefix: String = ""
    /**
     * 主键(ID)
     */
    var primaryKey: String = ""

    /**
     * 主键类型
     */
    var needPrimaryKeyType: String = ""

    /**
     * 源码目录
     */
    var sourceCodeTargetFolder: String = ""

    /**
     * 测试源码目录
     */
    var testCodeTargetFolder: String = ""

    /**
     * 配置文件资源目录
     */
    var resourcesTargetFolder: String = ""

    /**
     * 根包名
     */
    var rootTargetPackage: String = ""

    /**
     * 自定义实体基类
     */
    var baseEntity: String = ""

    /**
     * 自定义Dao通用接口
     */
    var baseDaoInterface: String = ""

    /**
     * 自定义Service通用接口
     */
    var baseServiceInterface: String = ""

    /**
     * 自定义ServiceImpl通用接口
     */
    var baseServiceImpl: String = ""

    /**
     * 实体类名
     */
    var entityClassName: String = ""

    /**
     * 实体类包名
     */
    var entityTargetPackage: String = ""

    /**
     * DTO类名
     */
    var dtoEntityClassName: String = ""

    /**
     * DTO类包名
     */
    var dtoEntityTargetPackage: String = ""

    /**
     * Mapper接口名
     */
    var mapperClassName: String = ""

    /**
     * Mapper接口包名
     */
    var mapperTargetPackage: String = ""

    /**
     * 映射XML文件名
     */
    var mappingFileName: String = ""

    /**
     * 映射XML文件目录
     */
    var mappingTargetFolder: String = ""

    /**
     * Service接口名
     */
    var serviceClassName: String = ""

    /**
     * Service接口包名
     */
    var serviceTargetPackage: String = ""

    /**
     * ServiceImpl名
     */
    var serviceImplClassName: String = ""

    /**
     * ServiceImpl包名
     */
    var serviceImplTargetPackage: String = ""

    /**
     * Controller类名
     */
    var controllerClassName: String = ""

    /**
     * Controller包名
     */
    var controllerTargetPackage: String = ""

    /**
     * Test类包名
     */
    var testTargetPackage: String = ""

    /**
     * Test类名
     */
    var testClassName: String = ""

    /**
     * JPA注解
     */
    var jpaAnnotationChoice: String = "内置"

    /**
     * 文件编码格式
     */
    var fileEncodingChoice: String = "UTF-8"

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

    /**
     * 静态常量属性
     */
    var needStaticPropVariable: Boolean = false

    /**
     * 动态创建SerialVersionID
     */
    var dynamicCreateSerialUID: Boolean = false

    /**
     * class文件目录
     */
    var binDir = ""

    /**
     * 编译时依赖的JAR目录
     */
    var extDirs: String = ""

    /**
     * 获取项目目录绝对路径
     */
    fun projectAbsolutePath(): String {
        return this.projectFolder + FileUtil.SLASH + this.moduleName + FileUtil.SLASH
    }

    /**
     * 获取项目源码目录绝对路径
     */
    fun sourceCodeAbsolutePath(): String {
        return projectAbsolutePath() + this.sourceCodeTargetFolder + FileUtil.SLASH
    }

    /**
     * 获取项目测试源码目录绝对路径
     */
    fun testCodeAbsolutePath(): String {
        return projectAbsolutePath() + this.testCodeTargetFolder + FileUtil.SLASH
    }

    /**
     * 获取项目配置文件目录绝对路径
     */
    fun resourcesAbsolutePath(): String {
        return projectAbsolutePath() + this.resourcesTargetFolder + FileUtil.SLASH
    }
    
    fun testResourcesAbsolutePath(): String {
        return projectAbsolutePath() + FileUtil.SLASH + "src" + FileUtil.SLASH + "test" + FileUtil.SLASH + "resources" + FileUtil.SLASH
    }

    /**
     * 获取Mapper.xml文件目录绝对路径
     */
    fun mapperXmlAbsolutePath(): String {
        return projectAbsolutePath() + this.mappingTargetFolder + FileUtil.SLASH
    }

    /**
     * 获取base包目录绝对路径
     */
    fun basePackageAbsolutePath(): String {
        return packageTransformAbsolutePath(basePackage())
    }

    /**
     * 获取Entity包目录绝对路径
     */
    fun entityPackageAbsolutePath(): String {
        return packageTransformAbsolutePath(entityPackage())
    }

    /**
     * 获取Dto包目录绝对路径
     */
    fun dtoPackageAbsolutePath(): String {
        return packageTransformAbsolutePath(dtoPackage())
    }

    /**
     * 获取Mapper包目录绝对路径
     */
    fun mapperPackageAbsolutePath(): String {
        return packageTransformAbsolutePath(mapperPackage())
    }

    /**
     * 获取Service包目录绝对路径
     */
    fun servicePackageAbsolutePath(): String {
        return packageTransformAbsolutePath(servicePackage())
    }

    /**
     * 获取ServiceImpl包目录绝对路径
     */
    fun serviceImplPackageAbsolutePath(): String {
        return packageTransformAbsolutePath(serviceImplPackage())
    }

    /**
     * 获取Controller包目录绝对路径
     */
    fun controllerPackageAbsolutePath(): String {
        return packageTransformAbsolutePath(controllerPackage())
    }

    /**
     * 获取测试包目录绝对路径
     */
    fun testCodePackageAbsolutePath(): String {
        val packageName = testPackage()
        return packageName.takeIf {
            it.isBlank()
        }?.run {
            sourceCodeAbsolutePath()
        } ?: run {
            if (packageName.contains(".")) {
                testCodeAbsolutePath() + packageName.replace(DOT_REGEX, FileUtil.SLASH) + FileUtil.SLASH
            } else {
                testCodeAbsolutePath() + packageName + FileUtil.SLASH
            }
        }
    }

    private fun packageTransformAbsolutePath(packageName: String): String {
        return packageName.takeIf {
            it.isBlank()
        }?.run {
            sourceCodeAbsolutePath()
        } ?: run {
            if (packageName.contains(".")) {
                sourceCodeAbsolutePath() + packageName.replace(DOT_REGEX, FileUtil.SLASH) + FileUtil.SLASH
            } else {
                sourceCodeAbsolutePath() + packageName + FileUtil.SLASH
            }
        }
    }
    
    fun configInit(): GeneratorConfig {
        this.basePackageTempVariable = basePackage()
        return this
    }

    /**
     * 获取Base包
     */
    fun basePackage(): String {
        return this.rootTargetPackage.takeIf {
            it.isBlank()
        }?.run {
            EMPTY
        } ?: run {
            if (this.rootTargetPackage.contains(MODULE_NAME)) {
                this.rootTargetPackage.trim().replace(MODULE_NAME, this.moduleName)
            } else {
                this.rootTargetPackage
            }
        }
    }

    /**
     * 获取Entity包
     */
    fun entityPackage(): String {
        return replaceBasePackage(this.entityTargetPackage)
    }

    /**
     * 获取DTO包
     */
    fun dtoPackage(): String {
        return replaceBasePackage(this.dtoEntityTargetPackage)
    }

    /**
     * 获取Mapper包
     */
    fun mapperPackage(): String {
        return replaceBasePackage(this.mapperTargetPackage)
    }

    /**
     * 获取Service包
     */
    fun servicePackage(): String {
        return replaceBasePackage(this.serviceTargetPackage)
    }

    /**
     * 获取ServiceImpl包
     */
    fun serviceImplPackage(): String {
        return replaceBasePackage(this.serviceImplTargetPackage)
    }

    /**
     * 获取Controller包
     */
    fun controllerPackage(): String {
        return replaceBasePackage(this.controllerTargetPackage)
    }

    /**
     * 获取Test包
     */
    fun testPackage(): String {
        return replaceBasePackage(this.testTargetPackage)
    }

    private fun replaceBasePackage(packageTemplate: String): String {
        return packageTemplate.takeIf {
            it.isBlank()
        }?.run {
            EMPTY
        } ?: run {
            if (packageTemplate.contains(BASE_PACKAGE)) {
                packageTemplate.trim().replace(BASE_PACKAGE, basePackageTempVariable)
            } else {
                packageTemplate
            }
        }
    }
    
    fun replaceDot(packageName: String): String {
        return packageName.replace(DOT_REGEX, FileUtil.SLASH)
    }

    /**
     * 使用内置JPA注解
     */
    fun useSystemJpa(): Boolean {
        return this.jpaAnnotationChoice == "BuiltIn"
    }

    /**
     * 使用Persistence-JPA注解
     */
    fun usePersistenceJpa(): Boolean {
        return this.jpaAnnotationChoice == "Persistence"
    }

    /**
     * 使用MyBatisPlus-JPA注解
     */
    fun useMyBatisPlusJpa(): Boolean {
        return this.jpaAnnotationChoice == "MybatisPlus"
    }
}
