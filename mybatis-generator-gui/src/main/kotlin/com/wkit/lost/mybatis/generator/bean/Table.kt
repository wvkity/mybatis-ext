package com.wkit.lost.mybatis.generator.bean

import com.wvkit.lost.mybatis.generator.utils.CaseFormat
import javafx.beans.property.SimpleStringProperty
import org.apache.logging.log4j.LogManager
import java.util.*
import kotlin.collections.ArrayList

class Table constructor(name: String) {
    companion object {
        private val LOG = LogManager.getLogger(Table)
    }

    /**
     * 表名(禁止修改)
     */
    private val name = SimpleStringProperty("")
    /**
     * 表名前缀
     */
    var prefix = SimpleStringProperty("")
    /**
     * 默认类名(禁止修改)
     */
    private val defaultClassName = SimpleStringProperty("")
    /**
     * 类名
     */
    var className = SimpleStringProperty("")
    /**
     * 注释
     */
    var comment = SimpleStringProperty("")
    /**
     * 作者
     */
    var author = SimpleStringProperty("")
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
        // TODO 把表前缀传进来 -- bug -- 2020-01-06 fix
        this.name.set(name)
        val initClassName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)
        this.defaultClassName.set(initClassName)
        this.classNameOverride = initClassName
        this.setClassName(initClassName)
        this.commentOverride = this.getComment()
        this.className.addListener { _, _, newClassName ->
            this.needImportJpa = (newClassName != getDefaultClassName())
        }
        // 监听表前缀变化
        this.prefix.addListener { _, _, prefixValue ->
            // 检查是否为空
            val tableName = getName()
            val realClassName = if (prefixValue.isNullOrBlank()) {
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName)
            } else {
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
                        tableName.substring(prefixValue.length))
            }
            this.defaultClassName.set(realClassName)
            // 对比是否有修改过类名
            if (!this.modified) {
                this.classNameOverride = realClassName
                this.className.set(realClassName)
            }
        }

        this.tablePrefixBridgeProperty.addListener { _, _, newTablePrefix ->
            val list = newTablePrefix.split(Regex(",\\s*"))
            if (list.isNotEmpty()) {
                // 忽略大小写比较表前缀
                val realTableName = getName().toLowerCase(Locale.ENGLISH)
                for (prefixValue in list) {
                    if (realTableName.startsWith(prefixValue.toLowerCase(Locale.ENGLISH))) {
                        this.prefix.set(prefixValue)
                        break
                    }
                }
            } else {
                this.prefix.set("")
            }
        }
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