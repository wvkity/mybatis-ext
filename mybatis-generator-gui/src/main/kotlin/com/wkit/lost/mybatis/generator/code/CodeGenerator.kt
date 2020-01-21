package com.wkit.lost.mybatis.generator.code

import com.wkit.lost.mybatis.generator.bean.GeneratorConfig
import com.wkit.lost.mybatis.generator.code.bean.Table
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

class CodeGenerator(generatorConfig: GeneratorConfig, tableData: List<Table>) {

    companion object {
        private val LOG = LogManager.getLogger(CodeGenerator)
        private val NEED_SPECIFIC_PRIMARY_KEY = listOf("UUID", "IDENTITY", "WORKER_SEQUENCE", "WORKER_SEQUENCE_STRING")
        private val NEW_LINE = System.getProperty("line.separator")
        private const val PACKAGE_REGEX = "([a-zA-Z0-9_.$]+)(<.*>)"
        private val PACKAGE_PATTERN = Pattern.compile(PACKAGE_REGEX)
        private const val GENERIC_CLASS_REGEX = "([a-zA-Z0-9_.$]+)(<(.*)>)"
        private val GENERIC_CLASS_PATTERN = Pattern.compile(GENERIC_CLASS_REGEX)
        private const val ENTITY = "{Entity}"
        private const val MAPPER = "{Mapper}"
        private const val ENTITY_DTO = "{EntityDTO}"
        private const val MYBATIS_PLUS_MODEL = "com.baomidou.mybatisplus.extension.activerecord.Model"
        private const val IMPORT_MYBATIS_PLUS_MODEL = "import $MYBATIS_PLUS_MODEL;"
        private const val SYS_MAPPER_INTERFACE = "com.wkit.lost.mybatis.mapper.MapperExecutor"
        private const val SYS_MAPPER_INTERFACE_NAME = "MapperExecutor"
        private const val SYS_MAPPER_INTERFACE_WITH_RESULT = "com.wkit.lost.mybatis.mapper.MapperExecutorWithSpecificResult"
        private const val SYS_MAPPER_INTERFACE_WITH_RESULT_NAME = "MapperExecutorWithSpecificResult"
        private const val PACKAGE_NAME_KEY = "PACKAGE_NAME"
        private const val TABLE_NAME_KEY = "TABLE_NAME"
        private const val CLASS_NAME_KEY = "CLASS_NAME"
        private const val IMPL_SERIALIZABLE_KEY = "IMPL_SERIALIZABLE"
        private const val NEW_LINE_KEY = "NEW_LINE"
        private const val TABLE_COMMENT_KEY = "TABLE_COMMENT"
        private const val AUTHOR_KEY = "AUTHOR"
        private const val COLUMNS_KEY = "COLUMNS"
        private const val NEED_COMMENT_KEY = "NEED_COMMENT"
        private const val TABLE_ANNOTATION_KEY = "TABLE_ANNOTATION"
        private const val USE_JPA_ANNOTATION_KEY = "USE_JPA_ANNOTATION"
        private const val IMPORTS_KEY = "IMPORTS"
        private const val USE_SWAGGER_ANNOTATION_KEY = "USE_SWAGGER_ANNOTATION"
        private const val PRIMARY_KEY_ANNOTATION_KEY = "PRIMARY_KEY_ANNOTATION"
        private const val DYNAMIC_CREATE_SERIAL_UID_KEY = "DYNAMIC_CREATE_SERIAL_UID"
        private const val LOMBOK_ANNOTATION_KEY = "LOMBOK_ANNOTATION"
        private const val EXT_CLASS_KEY = "EXT_CLASS"
        private const val USE_REPOSITORY_ANNOTATION_KEY = "USE_REPOSITORY_ANNOTATION"
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
        // 生成所需文件夹
        try {
            LOG.info("正在初始化相关配置...")
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
                }
            }
        } catch (e: Exception) {
            LOG.error("Code generation failed: {}", e.message, e)
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
        params[USE_JPA_ANNOTATION_KEY] = config.entityUseJpaAnnotation
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
        params[TABLE_ANNOTATION_KEY] = tableAnnotation
        // 公共部分
        entityCommons(table, params, importJavaTypes, GenerateType.ENTITY)
        // 继承
        val isExtends = createExtClass(table, params, importJavaTypes, config.entityImplSerializable,
                GenerateType.ENTITY, config.baseEntity)
        lombokPlugin(params, importJavaTypes, isExtends)
        val imports = ArrayList(importJavaTypes)
        imports.sort()
        params[IMPORTS_KEY] = imports.joinToString(NEW_LINE)
        params[PRIMARY_KEY_ANNOTATION_KEY] = primaryKeyAnnotation
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
        params[IMPORTS_KEY] = imports.joinToString(NEW_LINE)
        val className = params[CLASS_NAME_KEY]!! as String
        val filePath = (config.dtoPackageAbsolutePath() + className + ".java")
        generatorFile(DefaultTemplate.DTOEntity.value, filePath, params)
        if (config.dtoImplSerializable && config.dynamicCreateSerialUID) {
            // 进行编译
            SourceCodeSerialUIDReplacement.replace(className, config.dtoPackageAbsolutePath(),
                    config.dtoPackage(), binDir, config.extDirs, config.fileEncodingChoice)
        }
    }

    /**
     * 生成Mapper接口文件
     */
    private fun generateMapperInterfaceFile(table: Table) {
        val params = HashMap<String, Any>()
        val importJavaTypes = HashSet<String>()
        val mapperName = config.mapperClassName.replace(ENTITY, table.className)
        params[TABLE_COMMENT_KEY] = table.comment
        params[AUTHOR_KEY] = table.author
        params[NEED_COMMENT_KEY] = config.needComment
        params[NEW_LINE_KEY] = NEW_LINE
        params[CLASS_NAME_KEY] = mapperName
        params[PACKAGE_NAME_KEY] = config.mapperPackage()
        if (config.useRepositoryAnnotation) {
            importJavaTypes.add("import org.springframework.stereotype.Repository;")
        }
        createExtClass(table, params, importJavaTypes, false, GenerateType.MAPPER, config.baseDaoInterface)
        val imports = ArrayList(importJavaTypes)
        imports.sort()
        params[IMPORTS_KEY] = imports.joinToString(NEW_LINE)
        params[USE_REPOSITORY_ANNOTATION_KEY] = config.useRepositoryAnnotation
        val filePath = (config.mapperPackageAbsolutePath() + mapperName + ".java")
        generatorFile(DefaultTemplate.Mapper.value, filePath, params)
    }

    private fun entityCommons(table: Table, params: MutableMap<String, Any>, importJavaTypes: MutableSet<String>,
                              type: GenerateType) {
        params[TABLE_NAME_KEY] = table.tableName
        if (type == GenerateType.ENTITY) {
            params[CLASS_NAME_KEY] = table.className
            params[PACKAGE_NAME_KEY] = config.entityPackage()
            params[IMPL_SERIALIZABLE_KEY] = table.entityImplSerializable
            params[USE_SWAGGER_ANNOTATION_KEY] = config.entityUseSwaggerAnnotation
            if (config.entityUseSwaggerAnnotation) {
                importJavaTypes.add("import io.swagger.annotations.ApiModel;")
                importJavaTypes.add("import io.swagger.annotations.ApiModelProperty;")
            }
        } else {
            params[PACKAGE_NAME_KEY] = config.dtoPackage()
            params[CLASS_NAME_KEY] = config.dtoEntityClassName.replace(ENTITY, table.className)
            params[IMPL_SERIALIZABLE_KEY] = table.dtoEntityImplSerializable
            params[USE_SWAGGER_ANNOTATION_KEY] = config.dtoUseSwaggerAnnotation
        }
        params[TABLE_COMMENT_KEY] = table.comment
        params[AUTHOR_KEY] = table.author
        params[COLUMNS_KEY] = table.columns
        params[NEED_COMMENT_KEY] = config.needComment
        params[NEW_LINE_KEY] = NEW_LINE
        params[DYNAMIC_CREATE_SERIAL_UID_KEY] = config.dynamicCreateSerialUID
        if (params[IMPL_SERIALIZABLE_KEY] != null) {
            importJavaTypes.add("import java.io.Serializable;")
        }
        // swagger注解
        if (params[USE_SWAGGER_ANNOTATION_KEY] != null) {
            importJavaTypes.add("import io.swagger.annotations.ApiModel;")
            importJavaTypes.add("import io.swagger.annotations.ApiModelProperty;")
        }
        importJavaTypes.addAll(table.importJavaTypes)
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
                baseExtClass = baseExtClass.replace(ENTITY, table.className)
            }
            if (baseExtClass.contains(ENTITY_DTO)) {
                baseExtClass = baseExtClass.replace(ENTITY_DTO, dtoEntityClassName)
                importJavaTypes.add("import " + config.dtoPackage() + dtoEntityClassName + ";")
            }
            // 处理xx.xxx.xxx.Class<xx.xxx.xxx.EntityClass, xx.xx.xxx.EntityDTO> -> Class<xx.xxx.xxx.EntityClass, xx.xx.xxx.EntityDTO>
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
            // 处理Class<xx.xxx.xxx.EntityClass, xx.xx.xxx.EntityDTO> -> Class<EntityClass, EntityDTO>
            if (GENERIC_CLASS_PATTERN.matcher(baseExtClass).matches()) {
                val genericClassString = baseExtClass.replace(Regex(GENERIC_CLASS_REGEX), "$3")
                if (genericClassString.contains(".")) {
                    val newClassList = ArrayList<String>()
                    genericClassString.split(Regex(",\\s*")).forEach { 
                        val genericClassName = it.substring(it.lastIndexOf(".") + 1)
                        // 检查是否存在同类名
                        if (importJavaTypes.find { javaType -> javaType.contains(".$genericClassName;") 
                                        && javaType != "import $it;" }.isNullOrBlank()) {
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
            if (isSerializable) {
                extClass += "implements Serializable "
            }
            isExtends = true
        } else {
            if (config.useMyBatisPlusJpa()) {
                extClass += "extends "
                when (type) {
                    GenerateType.ENTITY -> {
                        extClass += if (importJavaTypes.find { it.contains(".Model;") 
                                        && it != IMPORT_MYBATIS_PLUS_MODEL }.isNullOrBlank()) {
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
                when (type) {
                    GenerateType.MAPPER -> {
                        importJavaTypes.add("import " + config.entityPackage() + "." + className + ";")
                        if (config.useDtoAsReturnValue) {
                            val dtoClassName = config.dtoEntityClassName.replace(ENTITY, className)
                            extClass += "extends $SYS_MAPPER_INTERFACE_WITH_RESULT_NAME<$className, $dtoClassName>"
                            importJavaTypes.add("import " + config.dtoPackage() + "." + dtoClassName + ";")
                            importJavaTypes.add("import $SYS_MAPPER_INTERFACE_WITH_RESULT;")
                        } else {
                            extClass += "extends $SYS_MAPPER_INTERFACE_NAME<${table.className}>"
                            importJavaTypes.add("import $SYS_MAPPER_INTERFACE;")
                        }
                    }
                }
            }
            if (isSerializable) {
                extClass += "implements Serializable "
            }
        }
        params[EXT_CLASS_KEY] = extClass
        return isExtends
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
        params[LOMBOK_ANNOTATION_KEY] = lombokAnnotation
    }

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
