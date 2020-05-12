package com.wkit.lost.mybatis.generator.code

import com.wkit.lost.mybatis.generator.bean.ConnectionConfig
import com.wkit.lost.mybatis.generator.bean.GeneratorConfig
import com.wkit.lost.mybatis.generator.code.bean.Table
import com.wkit.lost.mybatis.generator.constants.DatabaseType
import com.wkit.lost.mybatis.generator.utils.FileUtil
import com.wvkit.lost.mybatis.generator.code.SourceCodeSerialUIDReplacement
import org.apache.logging.log4j.LogManager
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.apache.velocity.app.VelocityEngine
import java.io.*
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

/**
 * 代码/项目生成器
 */
class Generator(generatorConfig: GeneratorConfig, tableData: List<Table>) {

    companion object {
        private val LOG = LogManager.getLogger(Generator)
        private val NEED_SPECIFIC_PRIMARY_KEY = listOf("UUID", "IDENTITY", "WORKER_SEQUENCE", "WORKER_SEQUENCE_STRING")
        private val NEW_LINE = System.getProperty("line.separator")
        private const val PACKAGE_REGEX = "([a-zA-Z0-9_.$]+)(<.*>)"
        private val PACKAGE_PATTERN = Pattern.compile(PACKAGE_REGEX)
        private const val GENERIC_CLASS_REGEX = "([a-zA-Z0-9_.$]+)(<(.*)>)"
        private val GENERIC_CLASS_PATTERN = Pattern.compile(GENERIC_CLASS_REGEX)
        private const val DEFAULT_GROUP_ID = "com.springboot.mybatis.example"
        private const val ENTITY = "{Entity}"
        private const val ENTITY_DTO = "{EntityDTO}"

        // mybatis-plus
        private const val MYBATIS_PLUS_MODEL = "com.baomidou.mybatisplus.extension.activerecord.Model"
        private const val IMPORT_MYBATIS_PLUS_MODEL = "import $MYBATIS_PLUS_MODEL;"

        // 系统类
        private const val SYS_MAPPER_INTERFACE = "com.wkit.lost.mybatis.mapper.MapperExecutor"
        private const val SYS_MAPPER_INTERFACE_NAME = "MapperExecutor"
        private const val SYS_MAPPER_INTERFACE_WITH_RESULT = "com.wkit.lost.mybatis.mapper.MapperExecutorCallable"
        private const val SYS_MAPPER_INTERFACE_WITH_RESULT_NAME = "MapperExecutorCallable"
        private const val SYS_SERVICE_INTERFACE = "com.wkit.lost.mybatis.service.ServiceExecutor"
        private const val SYS_SERVICE_INTERFACE_NAME = "ServiceExecutor"
        private const val SYS_SERVICE_INTERFACE_WITH_RESULT = "com.wkit.lost.mybatis.service.ServiceExecutorCallable"
        private const val SYS_SERVICE_INTERFACE_WITH_RESULT_NAME = "ServiceExecutorCallable"
        private const val SYS_SERVICE_IMPL = "com.wkit.lost.mybatis.service.AbstractServiceExecutor"
        private const val SYS_SERVICE_IMPL_NAME = "AbstractServiceExecutor"
        private const val SYS_SERVICE_IMPL_WITH_RESULT = "com.wkit.lost.mybatis.service.AbstractServiceExecutorCallable"
        private const val SYS_SERVICE_IMPL_WITH_RESULT_NAME = "AbstractServiceExecutorCallable"
        private const val KEY_PACKAGE_NAME = "PACKAGE_NAME"
        private const val KEY_TABLE_NAME = "TABLE_NAME"
        private const val KEY_CLASS_NAME = "CLASS_NAME"
        private const val KEY_MAPPER_CLASS_NAME = "MAPPER_CLASS_NAME"
        private const val KEY_IMPL_SERIALIZABLE = "IMPL_SERIALIZABLE"
        private const val KEY_NEW_LINE = "NEW_LINE"
        private const val KEY_TABLE_COMMENT = "TABLE_COMMENT"
        private const val KEY_AUTHOR = "AUTHOR"
        private const val KEY_COLUMNS = "COLUMNS"
        private const val KEY_NEED_COMMENT = "NEED_COMMENT"
        private const val KEY_TABLE_ANNOTATION = "TABLE_ANNOTATION"
        private const val KEY_USE_JPA_ANNOTATION = "USE_JPA_ANNOTATION"
        private const val KEY_IMPORTS = "IMPORTS"
        private const val KEY_USE_SWAGGER_ANNOTATION = "USE_SWAGGER_ANNOTATION"
        private const val KEY_PRIMARY_KEY_ANNOTATION = "PRIMARY_KEY_ANNOTATION"
        private const val KEY_DYNAMIC_CREATE_SERIAL_UID = "DYNAMIC_CREATE_SERIAL_UID"
        private const val KEY_LOMBOK_ANNOTATION = "LOMBOK_ANNOTATION"
        private const val KEY_EXT_CLASS = "EXT_CLASS"
        private const val KEY_USE_REPOSITORY_ANNOTATION = "USE_REPOSITORY_ANNOTATION"
        private const val KEY_SERVICE_CLASS = "SERVICE_CLASS"
        private const val KEY_GROUP_ID = "GROUP_ID"
        private const val KEY_ANNOTATIONS = "ANNOTATIONS"
        private const val KEY_PROJECT_NAME = "PROJECT_NAME"
        private const val KEY_DRIVER_CLASS_NAME = "DRIVER_CLASS_NAME"
        private const val KEY_JDBC_URL = "JDBC_URL"
        private const val KEY_USER_NAME = "USER_NAME"
        private const val KEY_PASSWORD = "PASSWORD"
        private const val KEY_DRIVER_REF = "DRIVER_REF"
    }

    private val config: GeneratorConfig = generatorConfig
    private val tables: List<Table> = tableData

    private enum class GenerateType {
        ENTITY,
        DTO,
        MAPPER,
        SERVICE,
        SERVICE_IMPL,
        CONTROLLER
    }


    private fun getVelocityEngine(): VelocityEngine {
        val velocityEngine = VelocityEngine()
        try {
            val props = Properties()
            props.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            props.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
            props.setProperty(Velocity.ENCODING_DEFAULT, config.fileEncodingChoice);
            props.setProperty(Velocity.INPUT_ENCODING, config.fileEncodingChoice);
            props.setProperty("resource.loader.file.unicode", "true")
            velocityEngine.setProperties(props)
        } catch (e: Exception) {
            // ignore
        }
        return velocityEngine
    }

    /**
     * 生成代码
     */
    fun generateCode() {
        // 生成所需文件
        build()
    }

    /**
     * 生成maven项目
     * @param connectionConfig 数据库连接配置
     */
    fun generateMavenProject(connectionConfig: ConnectionConfig?) {
        // 生成所需文件
        build()
    }

    /**
     * 生成Gradle项目
     * @param connectionConfig 数据库连接配置
     */
    fun generateGradleProject(connectionConfig: ConnectionConfig?) {
        // 生成所需文件
        build()
        // 生成build.gradle文件
        val params = HashMap<String, Any>()
        params[KEY_GROUP_ID] = if (config.uniqueGroupId.isBlank()) {
            DEFAULT_GROUP_ID
        } else {
            config.uniqueGroupId
        }
        if (connectionConfig != null) {
            when (DatabaseType.valueOf(connectionConfig.dbType!!)) {
                DatabaseType.MYSQL -> params[KEY_DRIVER_REF] = "implementation 'mysql:mysql-connector-java:5.1.48'$NEW_LINE"
                DatabaseType.MYSQL_8 -> params[KEY_DRIVER_REF] = "implementation 'mysql:mysql-connector-java:8.0.15'$NEW_LINE"
                DatabaseType.SQLITE -> params[KEY_DRIVER_REF] = "implementation 'org.xerial:sqlite-jdbc:3.28.0'$NEW_LINE"
                DatabaseType.SQL_SERVER -> params[KEY_DRIVER_REF] = "implementation 'com.microsoft.sqlserver:mssql-jdbc:7.4.1.jre8'$NEW_LINE"
                DatabaseType.ORACLE -> params[KEY_DRIVER_REF] = "implementation 'com.github.noraui:ojdbc8:12.2.0.1'$NEW_LINE"
                DatabaseType.POSTGRESQL -> params[KEY_DRIVER_REF] = "implementation 'org.postgresql:postgresql:42.2.8'$NEW_LINE"
            }
        }
        val gradleFilePath = config.projectAbsolutePath() + "build.gradle"
        generatorFile(DefaultTemplate.GradleFile.value, gradleFilePath, params)
        buildRunnerFile(connectionConfig)
    }

    private fun buildRunnerFile(connectionConfig: ConnectionConfig?) {
        val basePackage = config.basePackage()
        val mapperPackage = config.mapperPackage()
        val params = HashMap<String, Any>()
        val imports = HashSet<String>()
        val annotations = LinkedHashSet<String>()
        if (config.useSystemJpa() || config.usePersistenceJpa()) {
            imports.add("import com.wkit.lost.mybatis.spring.boot.plugin.EnableInterceptors;")
            annotations.add("@EnableInterceptors")
            imports.add("import com.wkit.lost.mybatis.spring.boot.data.auditing.EnableMetadataAuditing;")
            annotations.add("@EnableMetadataAuditing")
        }
        imports.add("import org.springframework.boot.autoconfigure.SpringBootApplication;")
        annotations.add("@SpringBootApplication")
        imports.add("import org.springframework.transaction.annotation.EnableTransactionManagement;")
        annotations.add("@EnableTransactionManagement")
        imports.add("import org.springframework.boot.context.properties.EnableConfigurationProperties;")
        annotations.add("@EnableConfigurationProperties")
        imports.add("import org.springframework.context.annotation.EnableAspectJAutoProxy;")
        annotations.add("@EnableAspectJAutoProxy( proxyTargetClass = true )")
        imports.add("import org.springframework.scheduling.annotation.EnableAsync;")
        annotations.add("@EnableAsync")
        imports.add("import org.springframework.boot.SpringApplication;")
        imports.add("import org.mybatis.spring.annotation.MapperScan;")
        if (mapperPackage.isNotBlank()) {
            annotations.add("@MapperScan( basePackages = { \"$mapperPackage\" } )")
        } else {
            annotations.add("@MapperScan")
        }
        val list = ArrayList(imports)
        list.sort()
        params[KEY_IMPORTS] = list.joinToString(NEW_LINE)
        params[KEY_ANNOTATIONS] = annotations.joinToString(NEW_LINE)
        val applicationFilePath = if (basePackage.isNotBlank()) {
            params[KEY_PACKAGE_NAME] = "package $basePackage;"
            config.basePackageAbsolutePath()
        } else {
            config.projectSrcAbsolutePath()
        } + "ApplicationRunner.java"
        generatorFile(DefaultTemplate.ApplicationFile.value, applicationFilePath, params)
        // 生成yml文件
        if (connectionConfig != null) {
            val databaseType = DatabaseType.valueOf(connectionConfig.dbType!!)
            val args = ArrayList<String>()
            connectionConfig.host?.run {
                args.add(this)
            }
            connectionConfig.port?.run {
                args.add(this)
            }
            connectionConfig.schema?.run {
                args.add(this)
            }
            connectionConfig.encoding?.run {
                args.add(this)
            }
            val jdbcUrl = databaseType.connectionUrlPattern.format(*args.toArray())
            val driverClassName = databaseType.driverClass
            val ymlParams = HashMap<String, Any>()
            ymlParams[KEY_JDBC_URL] = jdbcUrl
            ymlParams[KEY_DRIVER_CLASS_NAME] = driverClassName
            if (databaseType != DatabaseType.SQLITE) {
                ymlParams[KEY_USER_NAME] = connectionConfig.userName!!
                ymlParams[KEY_PASSWORD] = connectionConfig.password!!
            }
            val ymlFilePath = config.resourcesAbsolutePath() + "application.yml"
            generatorFile(DefaultTemplate.ApplicationYmlFile.value, ymlFilePath, ymlParams)
        }
        // 生成log4j2文件
        /*val logParams = HashMap<String, Any>()
        logParams[KEY_PROJECT_NAME] = config.moduleName
        val log4j2FilePath = config.resourcesAbsolutePath() + "log4j2.xml"
        generatorFile(DefaultTemplate.Log4j2File.value, log4j2FilePath, logParams)*/
    }

    private fun build() {
        LOG.info("正在初始化生成文件相关配置...")
        config.configInit()
        LOG.info("配置项初始化完成.")
        LOG.info("正在创建相关文件目录...")
        generateDir()
        /*// 创建编译目录
        val compileDir = config.binDir + System.currentTimeMillis() + FileUtil.SLASH
        val binDir = compileDir + "bin" + FileUtil.SLASH
        // 创建输出目录
        FileUtil.mkdir(binDir)*/
        LOG.info("所需文件目录创建完成.")
        LOG.info("准备生成代码，过程所需时间稍微有点长，请耐心等候......")
        tables.forEach {
            it.entityImplSerializable = config.entityImplSerializable
            it.dtoEntityImplSerializable = config.dtoImplSerializable
            it.packageName = config.entityPackage()
            // 生成Entity文件
            generateEntityFile(it, "")
            // 生成DTO实体类
            if (config.needDtoEntity) {
                generateDtoEntityFile(it, "")
            }
            // 生成Mapper接口文件
            if (config.needDaoInterface) {
                generateMapperInterfaceFile(it)
                generateMapperXmlFile(it)
            }
            // 生成Service文件
            if (config.needServiceInterface) {
                generateServiceInterfaceFile(it)
                generateServiceImplFile(it)
            }
            // 生成controller
            if (config.needController) {
                generateControllerFile(it)
            }
        }
    }

    /**
     * 生成实体类文件
     */
    private fun generateEntityFile(table: Table, binDir: String) {
        val params = HashMap<String, Any>()
        val importJavaTypes = HashSet<String>()
        var tableAnnotation = ""
        var primaryKeyAnnotation = ""
        // 检查是否使用JPA注解
        val jpaAnnotationType = config.jpaAnnotationChoice
        params[KEY_USE_JPA_ANNOTATION] = config.entityUseJpaAnnotation
        if (jpaAnnotationType != "NONE" && config.entityUseJpaAnnotation) {
            val needSpecificType = NEED_SPECIFIC_PRIMARY_KEY.contains(config.needPrimaryKeyType)
            // 判断JPA类型
            if (config.useSystemJpa()) {
                // 内置
                tableAnnotation = "@Table(name = \"${table.tableName}\""
                importJavaTypes.add("import com.wkit.lost.mybatis.annotation.Table;")
                if (table.hasPrimaryKey) {
                    primaryKeyAnnotation = "@Id"
                    importJavaTypes.add("import com.wkit.lost.mybatis.annotation.Id;")
                    if (needSpecificType) {
                        primaryKeyAnnotation += "$NEW_LINE    @GeneratedValue(generator = \"${config.needPrimaryKeyType}\")"
                        importJavaTypes.add("import com.wkit.lost.mybatis.annotation.GeneratedValue;")
                    }
                }
            } else if (config.usePersistenceJpa()) {
                // Javax
                tableAnnotation = "@Table(name = \"${table.tableName}\""
                importJavaTypes.add("import javax.persistence.Table;")
                if (table.hasPrimaryKey) {
                    primaryKeyAnnotation = "@Id"
                    if (needSpecificType) {
                        primaryKeyAnnotation += "$NEW_LINE    @GeneratedValue(generator = \"${config.needPrimaryKeyType}\")"
                        importJavaTypes.add("import javax.persistence.GeneratedValue;")
                    }
                    importJavaTypes.add("import javax.persistence.Id;")
                }
            } else if (config.useMyBatisPlusJpa()) {
                // Mybatis-plus
                tableAnnotation = "@TableName(value = \"${table.tableName}\""
                importJavaTypes.add("import com.baomidou.mybatisplus.annotation.TableName;")
            }
            table.schema.takeIf {
                it.isNotBlank() && config.useSchemaPrefix
            }?.run {
                tableAnnotation += ", schema = \"${table.schema}\")"
            } ?: run {
                tableAnnotation += ")"
            }
        }
        importJavaTypes.addAll(table.importAnnotationJavaTypes)
        params[KEY_TABLE_ANNOTATION] = tableAnnotation
        // 公共部分
        entityCommons(table, params, importJavaTypes, GenerateType.ENTITY)
        // 继承
        val isExtends = createExtClass(table, params, importJavaTypes, config.entityImplSerializable,
                GenerateType.ENTITY, config.baseEntity)
        lombokPlugin(params, importJavaTypes, isExtends)
        val imports = ArrayList(importJavaTypes)
        imports.sort()
        params[KEY_IMPORTS] = imports.joinToString(NEW_LINE)
        params[KEY_PRIMARY_KEY_ANNOTATION] = primaryKeyAnnotation
        val filePath = (config.entityPackageAbsolutePath() + table.className + ".java")
        generatorFile(DefaultTemplate.Entity.value, filePath, params)
        if (config.entityImplSerializable && config.dynamicCreateSerialUID) {
            // 进行编译
            SourceCodeSerialUIDReplacement.replace(table.className, config.entityPackageAbsolutePath(),
                    config.entityPackage(), binDir, config.extDirs, config.fileEncodingChoice)
        }
    }

    /**
     * 生成DTO实体类
     */
    private fun generateDtoEntityFile(table: Table, binDir: String) {
        val params = HashMap<String, Any>()
        val importJavaTypes = HashSet<String>()
        entityCommons(table, params, importJavaTypes, GenerateType.DTO)
        createExtClass(table, params, importJavaTypes, config.dtoImplSerializable,
                GenerateType.DTO, "")
        lombokPlugin(params, importJavaTypes, false)
        val imports = ArrayList(importJavaTypes)
        imports.sort()
        params[KEY_IMPORTS] = imports.joinToString(NEW_LINE)
        val className = params[KEY_CLASS_NAME]!! as String
        val filePath = (config.dtoPackageAbsolutePath() + className + ".java")
        generatorFile(DefaultTemplate.DTOEntity.value, filePath, params)
        if (config.dtoImplSerializable && config.dynamicCreateSerialUID) {
            // 进行编译
            SourceCodeSerialUIDReplacement.replace(className, config.dtoPackageAbsolutePath(),
                    config.dtoPackage(), binDir, config.extDirs, config.fileEncodingChoice)
        }
    }

    /**
     * 生成实体类公共方法
     */
    private fun entityCommons(table: Table, params: MutableMap<String, Any>, importJavaTypes: MutableSet<String>,
                              type: GenerateType) {
        params[KEY_TABLE_NAME] = table.tableName
        if (type == GenerateType.ENTITY) {
            params[KEY_CLASS_NAME] = table.className
            params[KEY_PACKAGE_NAME] = config.entityPackage()
            params[KEY_IMPL_SERIALIZABLE] = table.entityImplSerializable
            params[KEY_USE_SWAGGER_ANNOTATION] = config.entityUseSwaggerAnnotation
        } else {
            params[KEY_PACKAGE_NAME] = config.dtoPackage()
            params[KEY_CLASS_NAME] = config.dtoEntityClassName.replace(ENTITY, table.className)
            params[KEY_IMPL_SERIALIZABLE] = table.dtoEntityImplSerializable
            params[KEY_USE_SWAGGER_ANNOTATION] = config.dtoUseSwaggerAnnotation
        }
        params[KEY_TABLE_COMMENT] = table.comment
        params[KEY_AUTHOR] = table.author
        params[KEY_COLUMNS] = table.columns
        params[KEY_NEED_COMMENT] = config.needComment
        params[KEY_NEW_LINE] = NEW_LINE
        params[KEY_DYNAMIC_CREATE_SERIAL_UID] = config.dynamicCreateSerialUID
        if (params[KEY_IMPL_SERIALIZABLE] != null) {
            importJavaTypes.add("import java.io.Serializable;")
        }
        // swagger注解
        if (params[KEY_USE_SWAGGER_ANNOTATION] != null) {
            importJavaTypes.add("import io.swagger.annotations.ApiModel;")
            importJavaTypes.add("import io.swagger.annotations.ApiModelProperty;")
        }
        importJavaTypes.addAll(table.importJavaTypes)
    }

    /**
     * lombok插件
     */
    private fun lombokPlugin(params: MutableMap<String, Any>, importJavaTypes: MutableSet<String>, isExtends: Boolean) {
        // lombok插件
        var lombokAnnotation = ""
        if (config.useLombokPlugin) {
            lombokAnnotation = "@Getter$NEW_LINE@Setter$NEW_LINE@Accessors( chain = true )$NEW_LINE@NoArgsConstructor$NEW_LINE@AllArgsConstructor"
            importJavaTypes.add("import lombok.NoArgsConstructor;")
            importJavaTypes.add("import lombok.AllArgsConstructor;")
            importJavaTypes.add("import lombok.Getter;")
            importJavaTypes.add("import lombok.Setter;")
            importJavaTypes.add("import lombok.experimental.Accessors;")
            if (config.needStaticPropVariable) {
                lombokAnnotation += "$NEW_LINE@FieldNameConstants"
                importJavaTypes.add("import lombok.experimental.FieldNameConstants;")
            }
            if (config.needToStringHashCodeEquals) {
                lombokAnnotation += "$NEW_LINE@ToString$NEW_LINE@EqualsAndHashCode{callSuper}"
                importJavaTypes.add("import lombok.ToString;")
                importJavaTypes.add("import lombok.EqualsAndHashCode;")
            }
            lombokAnnotation = if (isExtends) {
                lombokAnnotation.replace("{callSuper}", "(callSuper = true)")
            } else {
                lombokAnnotation.replace("{callSuper}", "")
            }
        }
        params[KEY_LOMBOK_ANNOTATION] = lombokAnnotation
    }

    /**
     * 生成Mapper接口文件
     */
    private fun generateMapperInterfaceFile(table: Table) {
        val params = HashMap<String, Any>()
        val importJavaTypes = HashSet<String>()
        val mapperName = config.mapperClassName.replace(ENTITY, table.className)
        params[KEY_TABLE_COMMENT] = table.comment
        params[KEY_AUTHOR] = table.author
        params[KEY_NEED_COMMENT] = config.needComment
        params[KEY_NEW_LINE] = NEW_LINE
        params[KEY_CLASS_NAME] = mapperName
        params[KEY_PACKAGE_NAME] = config.mapperPackage()
        params[KEY_TABLE_COMMENT] = table.comment
        if (config.useRepositoryAnnotation) {
            importJavaTypes.add("import org.springframework.stereotype.Repository;")
        }
        createExtClass(table, params, importJavaTypes, false, GenerateType.MAPPER, config.baseDaoInterface)
        val imports = ArrayList(importJavaTypes)
        imports.sort()
        params[KEY_IMPORTS] = imports.joinToString(NEW_LINE)
        params[KEY_USE_REPOSITORY_ANNOTATION] = config.useRepositoryAnnotation
        val filePath = (config.mapperPackageAbsolutePath() + mapperName + ".java")
        generatorFile(DefaultTemplate.Mapper.value, filePath, params)
    }

    /**
     * 生成Mapper.xml文件
     */
    private fun generateMapperXmlFile(table: Table) {
        val params = HashMap<String, Any>()
        val className = table.className
        val fileName = config.mappingFileName.replace(ENTITY, className)
        val filePrefix = fileName.substring(0, fileName.indexOf(".") - 1)
        params[KEY_CLASS_NAME] = config.entityPackage() + "." + className
        params[KEY_MAPPER_CLASS_NAME] = config.mapperPackage() + "." + config.mapperClassName.replace(ENTITY, className)
        // 检查是否覆盖
        val realFileName = if (config.overrideXml) {
            // 删除文件
            val file = File(config.mapperXmlAbsolutePath() + fileName)
            if (file.exists()) {
                file.delete()
            }
            fileName
        } else {
            val size = readFileSize(config.mapperXmlAbsolutePath(), filePrefix.toUpperCase(Locale.ENGLISH))
            filePrefix + "_" + size + ".xml"
        }
        val filePath = (config.mapperXmlAbsolutePath() + realFileName)
        // 列
        params[KEY_COLUMNS] = table.columns
        generatorFile(DefaultTemplate.MapperFile.value, filePath, params)
    }

    private fun readFileSize(dir: String, filePrefix: String): Int {
        val folder = File(dir)
        if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()
            if (files != null && files.isNotEmpty()) {
                var size = 0
                files.forEach {
                    val fileName = it.name.toUpperCase(Locale.ENGLISH)
                    if (fileName.startsWith(filePrefix)) {
                        size++
                    }
                }
                return size
            }
        }
        return 0
    }

    /**
     * 生成Service接口文件
     */
    private fun generateServiceInterfaceFile(table: Table) {
        val params = HashMap<String, Any>()
        val importJavaTypes = HashSet<String>()
        val serviceName = config.serviceClassName.replace(ENTITY, table.className)
        params[KEY_AUTHOR] = table.author
        params[KEY_NEED_COMMENT] = config.needComment
        params[KEY_NEW_LINE] = NEW_LINE
        params[KEY_CLASS_NAME] = serviceName
        params[KEY_PACKAGE_NAME] = config.servicePackage()
        params[KEY_TABLE_COMMENT] = table.comment
        createExtClass(table, params, importJavaTypes, false, GenerateType.SERVICE, config.baseServiceInterface)
        val imports = ArrayList(importJavaTypes)
        imports.sort()
        params[KEY_IMPORTS] = imports.joinToString(NEW_LINE)
        val filePath = (config.servicePackageAbsolutePath() + serviceName + ".java")
        generatorFile(DefaultTemplate.Service.value, filePath, params)
    }

    /**
     * 生成Service实现类文件
     */
    private fun generateServiceImplFile(table: Table) {
        val params = HashMap<String, Any>()
        val importJavaTypes = HashSet<String>()
        val serviceImplName = config.serviceImplClassName.replace(ENTITY, table.className)
        params[KEY_AUTHOR] = table.author
        params[KEY_NEED_COMMENT] = config.needComment
        params[KEY_NEW_LINE] = NEW_LINE
        params[KEY_CLASS_NAME] = serviceImplName
        params[KEY_PACKAGE_NAME] = config.serviceImplPackage()
        importJavaTypes.add("import org.springframework.stereotype.Service;")
        createExtClass(table, params, importJavaTypes, false, GenerateType.SERVICE_IMPL, config.baseServiceImpl)
        val imports = ArrayList(importJavaTypes)
        imports.sort()
        params[KEY_IMPORTS] = imports.joinToString(NEW_LINE)
        params[KEY_TABLE_COMMENT] = table.comment
        val filePath = (config.serviceImplPackageAbsolutePath() + serviceImplName + ".java")
        generatorFile(DefaultTemplate.ServiceImpl.value, filePath, params)
    }

    /**
     * 生成Controller类文件
     */
    private fun generateControllerFile(table: Table) {
        val params = HashMap<String, Any>()
        val importJavaTypes = HashSet<String>()
        val controllerName = config.controllerClassName.replace(ENTITY, table.className)
        params[KEY_AUTHOR] = table.author
        params[KEY_NEED_COMMENT] = config.needComment
        params[KEY_NEW_LINE] = NEW_LINE
        params[KEY_CLASS_NAME] = controllerName
        params[KEY_PACKAGE_NAME] = config.controllerPackage()
        importJavaTypes.add("import org.springframework.stereotype.Controller;")
        createExtClass(table, params, importJavaTypes, false, GenerateType.CONTROLLER, "")
        val imports = ArrayList(importJavaTypes)
        imports.sort()
        params[KEY_IMPORTS] = imports.joinToString(NEW_LINE)
        params[KEY_TABLE_COMMENT] = table.comment
        val filePath = (config.controllerPackageAbsolutePath() + controllerName + ".java")
        generatorFile(DefaultTemplate.Controller.value, filePath, params)
    }

    /**
     * 继承类
     */
    private fun createExtClass(table: Table, params: MutableMap<String, Any>, importJavaTypes: MutableSet<String>,
                               isSerializable: Boolean, type: GenerateType, customExtClass: String): Boolean {
        // 继承基类
        var extClass = " "
        var baseExtClass = customExtClass
        var isExtends = false
        // 处理自定义基类
        val className = table.className
        val dtoEntityClassName = config.dtoEntityClassName.replace(ENTITY, className)
        if (baseExtClass.isNotBlank()) {
            if (baseExtClass.contains(ENTITY)) {
                baseExtClass = baseExtClass.replace(ENTITY, className)
            }
            if (baseExtClass.contains(ENTITY_DTO)) {
                baseExtClass = baseExtClass.replace(ENTITY_DTO, dtoEntityClassName)
                importJavaTypes.add("import " + config.dtoPackage() + dtoEntityClassName + ";")
            }
            // 处理xx.xxx.xxx.ClassName<xx.xxx.xxx.EntityClass, xx.xx.xxx.EntityDTO> -> ClassName<xx.xxx.xxx.EntityClass, xx.xx.xxx.EntityDTO>
            // 检查导入的jar中是否包含了同类名
            if (PACKAGE_PATTERN.matcher(baseExtClass).matches()) {
                val regex = Regex(PACKAGE_REGEX)
                val classFullName = baseExtClass.replace(regex, "$1")
                if (classFullName.contains(".")) {
                    val realClassName = classFullName.substring(classFullName.lastIndexOf(".") + 1)
                    if (importJavaTypes.find { it.contains(".$realClassName;") && it != "import $classFullName;" }.isNullOrBlank()) {
                        importJavaTypes.add("import $classFullName;")
                        baseExtClass = realClassName + baseExtClass.replace(regex, "$2")
                    }
                }
            }
            // 处理ClassName<xx.xxx.xxx.EntityClass, xx.xx.xxx.EntityDTO> -> ClassName<EntityClass, EntityDTO>
            if (GENERIC_CLASS_PATTERN.matcher(baseExtClass).matches()) {
                val genericClassString = baseExtClass.replace(Regex(GENERIC_CLASS_REGEX), "$3")
                if (genericClassString.contains(".")) {
                    val newClassList = ArrayList<String>()
                    genericClassString.split(Regex(",\\s*")).forEach {
                        val genericClassName = it.substring(it.lastIndexOf(".") + 1)
                        // 检查是否存在同类名
                        if (importJavaTypes.find { javaType ->
                                    javaType.contains(".$genericClassName;")
                                            && javaType != "import $it;"
                                }.isNullOrBlank()) {
                            newClassList.add(genericClassName)
                            importJavaTypes.add("import $it;")
                        } else {
                            newClassList.add(it)
                        }
                    }
                    baseExtClass = baseExtClass.replace(genericClassString, newClassList.joinToString(", "))
                }
            }
            extClass += "extends $baseExtClass "
        }
        if (extClass.isNotBlank()) {
            // 自定义base类(Mapper、Service、ServiceImpl)
            if (isSerializable) {
                extClass += "implements Serializable "
            }
            isExtends = true
        } else {
            if (config.useMyBatisPlusJpa()) {
                extClass += "extends "
                when (type) {
                    GenerateType.ENTITY -> {
                        extClass += if (importJavaTypes.find {
                                    it.contains(".Model;")
                                            && it != IMPORT_MYBATIS_PLUS_MODEL
                                }.isNullOrBlank()) {
                            importJavaTypes.add(IMPORT_MYBATIS_PLUS_MODEL)
                            "Model<$className> "
                        } else {
                            "$MYBATIS_PLUS_MODEL<$className> "
                        }
                        isExtends = true
                    }
                    GenerateType.MAPPER -> {
                    }
                }
            } else {
                val dtoClassName = config.dtoEntityClassName.replace(ENTITY, className)
                when (type) {
                    GenerateType.MAPPER -> {
                        importJavaTypes.add("import " + config.entityPackage() + ".$className;")
                        if (config.useDtoAsReturnValue) {
                            extClass += "extends $SYS_MAPPER_INTERFACE_WITH_RESULT_NAME<$className, $dtoClassName>"
                            importJavaTypes.add("import " + config.dtoPackage() + "." + dtoClassName + ";")
                            importJavaTypes.add("import $SYS_MAPPER_INTERFACE_WITH_RESULT;")
                        } else {
                            extClass += "extends $SYS_MAPPER_INTERFACE_NAME<$className>"
                            importJavaTypes.add("import $SYS_MAPPER_INTERFACE;")
                        }
                    }
                    GenerateType.SERVICE -> {
                        importJavaTypes.add("import " + config.entityPackage() + ".$className;")
                        if (config.useDtoAsReturnValue) {
                            extClass += "extends $SYS_SERVICE_INTERFACE_WITH_RESULT_NAME<$className, $dtoClassName>"
                            importJavaTypes.add("import " + config.dtoPackage() + ".$dtoClassName;")
                            importJavaTypes.add("import $SYS_SERVICE_INTERFACE_WITH_RESULT;")
                        } else {
                            extClass += "extends $SYS_SERVICE_INTERFACE_NAME<$className>"
                            importJavaTypes.add("import $SYS_SERVICE_INTERFACE;")
                        }
                    }
                    GenerateType.SERVICE_IMPL -> {
                        // mapper
                        val mapperClass = config.mapperClassName.replace(ENTITY, className)
                        importJavaTypes.add("import " + config.mapperPackage() + ".$mapperClass;")
                        // service
                        val serviceClass = config.serviceClassName.replace(ENTITY, className)
                        importJavaTypes.add("import " + config.servicePackage() + ".$serviceClass;")
                        importJavaTypes.add("import " + config.entityPackage() + ".$className;")
                        if (config.useDtoAsReturnValue) {
                            extClass += "extends $SYS_SERVICE_IMPL_WITH_RESULT_NAME<$mapperClass, $className, $dtoClassName> implements $serviceClass"
                            importJavaTypes.add("import " + config.dtoPackage() + ".$dtoClassName;")
                            importJavaTypes.add("import $SYS_SERVICE_IMPL_WITH_RESULT;")
                        } else {
                            extClass += "extends $SYS_SERVICE_IMPL_NAME<$mapperClass, $className> implements $serviceClass"
                            importJavaTypes.add("import $SYS_SERVICE_IMPL;")
                        }
                    }
                    GenerateType.CONTROLLER -> {
                        // service
                        val serviceClass = config.serviceClassName.replace(ENTITY, className)
                        val serviceBeanName = serviceClass.toCharArray()[0].toLowerCase() + serviceClass.substring(1)
                        importJavaTypes.add("import " + config.servicePackage() + ".$serviceClass;")
                        if (config.useLombokPlugin) {
                            importJavaTypes.add("import lombok.AllArgsConstructor;")
                            params[KEY_LOMBOK_ANNOTATION] = "@AllArgsConstructor"
                            params[KEY_SERVICE_CLASS] = "$NEW_LINE    private final $serviceClass $serviceBeanName;$NEW_LINE"
                        } else {
                            params[KEY_LOMBOK_ANNOTATION] = ""
                            importJavaTypes.add("import org.springframework.beans.factory.annotation.Autowired;")
                            params[KEY_SERVICE_CLASS] = "$NEW_LINE    @Autowired${NEW_LINE}    private $serviceClass $serviceBeanName;$NEW_LINE"
                        }
                    }
                }
            }
            if (isSerializable) {
                extClass += "implements Serializable "
            }
        }
        params[KEY_EXT_CLASS] = extClass
        return isExtends
    }


    /**
     * 生成文件
     */
    private fun generatorFile(templatePath: String, filePath: String, params: MutableMap<String, Any>) {
        val template = getVelocityEngine().getTemplate(templatePath, config.fileEncodingChoice)
        val file = File(filePath)
        if (!file.exists()) {
            val parentFile = file.parentFile
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
        }
        val writer = PrintWriter(BufferedWriter(OutputStreamWriter(FileOutputStream(file), config.fileEncodingChoice)))
        writer.use {
            template.merge(VelocityContext(params), it)
            writer.flush()
        }
    }

    /**
     * 生成文件目录
     */
    private fun generateDir() {
        try {
            FileUtil.mkdir(config.basePackageAbsolutePath())
            FileUtil.mkdir(config.resourcesAbsolutePath())
            FileUtil.mkdir(config.testResourcesAbsolutePath())
            FileUtil.mkdir(config.testCodePackageAbsolutePath())
            FileUtil.mkdir(config.entityPackageAbsolutePath())
            if (config.needDtoEntity) {
                FileUtil.mkdir(config.dtoPackageAbsolutePath())
            }
            if (config.needDaoInterface) {
                FileUtil.mkdir(config.mapperPackageAbsolutePath())
                FileUtil.mkdir(config.mapperXmlAbsolutePath())
            }
            if (config.needServiceInterface) {
                FileUtil.mkdir(config.servicePackageAbsolutePath())
                FileUtil.mkdir(config.serviceImplPackageAbsolutePath())
            }
            if (config.needController) {
                FileUtil.mkdir(config.controllerPackageAbsolutePath())
            }
        } catch (e: Exception) {
            LOG.error("File directory creation failed: {}", e.message, e)
        }
    }
}
