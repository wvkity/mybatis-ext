package com.wkit.lost.mybatis.generator.bean

import com.wvkit.lost.mybatis.generator.utils.CaseFormat
import javafx.beans.property.SimpleStringProperty
import org.apache.logging.log4j.LogManager
import java.util.*
import kotlin.collections.ArrayList

/**
 * 表信息
 * @param originalTableName 表名
 * @param originalComment 表注释
 * @param tablePrefix 表名前缀(忽略大小写，多个使用英文逗号隔开)
 * @author wvkity
 */
class Table constructor(originalTableName: String, originalComment: String, tablePrefix: String, private var originalSchema: String) {
    companion object {
        private val LOG = LogManager.getLogger(Table)
    }
    
    private val schema = originalSchema

    /**
     * 表名(禁止修改)
     */
    private val name = SimpleStringProperty("")
    /**
     * 表名前缀
     */
    private val prefix = SimpleStringProperty("")
    /**
     * 默认类名(禁止修改)
     */
    private val defaultClassName = SimpleStringProperty("")
    /**
     * 类名
     */
    private val className = SimpleStringProperty("")
    /**
     * 注释
     */
    private val comment = SimpleStringProperty("")
    /**
     * 作者
     */
    private val author = SimpleStringProperty("")
    /**
     * 列
     */
    var columns = ArrayList<Column>()

    //////////////
    private var modified = false
    private var classNameOverride = ""
    private var commentOverride = ""
    private var needImportJpa = false
    private val tablePrefixBridgeProperty = SimpleStringProperty("")
    //////////////

    init {
        this.name.set(originalTableName)
        processTablePrefix(originalTableName, tablePrefix)
        this.commentOverride = originalComment
        this.comment.set(originalComment)
        this.className.addListener { _, _, newClassName ->
            this.needImportJpa = (newClassName != getDefaultClassName())
        }
        // 监听表前缀变化
        this.prefix.addListener { _, _, prefixValue ->
            // 检查是否为空
            val tableName = getName()
            val realClassName = if (prefixValue.isNullOrBlank()) {
                CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase(Locale.ENGLISH))
            } else {
                CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
                        tableName.substring(prefixValue.length).toLowerCase(Locale.ENGLISH))
            }
            this.defaultClassName.set(realClassName)
            // 对比是否有修改过类名
            if (!this.modified) {
                this.classNameOverride = realClassName
                this.className.set(realClassName)
            }
        }

        this.tablePrefixBridgeProperty.addListener { _, _, newTablePrefix ->
            val list = newTablePrefix.trim().split(Regex(",\\s*"))
            if (list.isNotEmpty()) {
                // 忽略大小写比较表前缀
                val realTableName = getName().toLowerCase(Locale.ENGLISH)
                var isMatch = false
                for (prefixValue in list) {
                    if (realTableName.startsWith(prefixValue.toLowerCase(Locale.ENGLISH))) {
                        this.prefix.set(prefixValue)
                        isMatch = true
                        break
                    }
                }
                // 检查是否匹配，不匹配且有前缀则进行还原
                if (!isMatch && getPrefix().isNotBlank()) {
                    this.prefix.set("")
                }
            } else {
                this.prefix.set("")
            }
        }
    }

    private fun processTablePrefix(originalTableName: String, tablePrefix: String) {
        val array = tablePrefix.trim().split(Regex(",\\s*"))
        val tableName = array.takeIf { 
            it.isNotEmpty()
        } ?.run { 
            val tableNameTemp = originalTableName.toUpperCase(Locale.ENGLISH)
            var realTableName = originalTableName
            for(prefixValue in array) {
                if (tableNameTemp.startsWith(prefixValue.toUpperCase(Locale.ENGLISH))) {
                    realTableName = originalTableName.substring(prefixValue.length)
                    prefix.set(prefixValue)
                    break
                }
            }
            realTableName
        } ?: run {
            originalTableName
        }
        val initClassName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase(Locale.ENGLISH))
        this.defaultClassName.set(initClassName)
        this.classNameOverride = initClassName
        this.setClassName(initClassName)
    }

    /**
     * 取消修改(回滚)
     */
    fun rollback() {
        this.classNameRollback()
        this.commentRollback()
        this.columns.forEach { it.rollback() }
    }

    /**
     * 确认修改
     */
    fun confirmChange() {
        this.commentOverride = getComment()
        this.classNameOverride = getClassName()
        // 标记类名已修改过，用于根据表名前缀动态修改类名
        if (getDefaultClassName() != this.classNameOverride && !modified) {
            this.modified = true
        }
        this.columns.forEach { it.confirmChange() }
    }

    private fun classNameRollback() {
        this.setClassName(this.classNameOverride)
    }

    private fun commentRollback() {
        this.setComment(this.commentOverride)
    }

    fun getName(): String {
        return this.name.get()
    }

    fun getPrefix(): String {
        return this.prefix.get()
    }

    fun setPrefix(prefix: String) {
        this.prefix.set(prefix)
    }

    fun getDefaultClassName(): String {
        return this.defaultClassName.get()
    }

    fun getClassName(): String {
        return this.className.get()
    }

    fun setClassName(className: String) {
        this.className.set(className)
    }

    fun getComment(): String {
        return this.comment.get()
    }

    fun setComment(comment: String) {
        this.comment.set(comment)
    }

    fun getAuthor(): String {
        return this.author.get()
    }

    fun setAuthor(author: String) {
        this.author.set(author)
    }
    
    fun getSchema(): String {
        return this.schema
    }

    fun getClassNameValue(): String {
        return this.classNameOverride.takeIf {
            it.isBlank()
        }?.run {
            getDefaultClassName()
        } ?: run {
            this.classNameOverride
        }
    }

    fun isNeedImportJpa(): Boolean {
        return this.needImportJpa
    }

    fun getTablePrefixBridgeProperty(): SimpleStringProperty {
        return this.tablePrefixBridgeProperty
    }
}