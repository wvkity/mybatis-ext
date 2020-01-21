package com.wkit.lost.mybatis.generator.bean;

import com.wkit.lost.mybatis.generator.mapping.JdbcJavaTypeRegistrar
import com.wvkit.lost.mybatis.generator.utils.CaseFormat
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import java.util.*

class Column constructor(originalColumnName: String, originalJdbcType: String, originalComment: String) {

    /**
     * 是否选择
     */
    private val checked = SimpleBooleanProperty(true)
    /**
     * 列名
     */
    private val columnName = SimpleStringProperty("")
    /**
     * 长度
     */
    private val columnLength = SimpleStringProperty("")
    /**
     * JDBC类型
     */
    private val jdbcType = SimpleStringProperty("")
    /**
     * Java类型
     */
    private val javaType = SimpleStringProperty("")
    /**
     * 属性名
     */
    private val propertyName = SimpleStringProperty("")
    /**
     * 注释
     */
    private val comment = SimpleStringProperty("")
    /**
     * 是否主键
     */
    private val primary = SimpleBooleanProperty(false)
    /**
     * 列索引
     */
    private val index = SimpleStringProperty("")
    /**
     * 导入的Java类型
     */
    private val importJavaType = SimpleStringProperty("")
    /**
     * 处理器
     */
    private val typeHandle = SimpleStringProperty("")

    //// 默认属性 ////
    private val defaultChecked = true
    private val defaultPropertyName: String
    private val defaultJavaType: String
    private val defaultImportJavaType: String
    private val defaultTypeHandle: String = ""
    private val defaultComment: String
    ///////////////

    /////// overrides ///////
    private var checkedOverride = ""
    private var javaTypeOverride = ""
    private var propertyNameOverride = ""
    private var importJavaTypeOverride = ""
    private var typeHandleOverride = ""
    private var commentOverride = ""
    ////////////////////////

    init {
        this.columnName.set(originalColumnName)
        this.jdbcType.set(originalJdbcType)
        val orgJavaType = JdbcJavaTypeRegistrar.javaTypeString(originalJdbcType.toLowerCase(Locale.ENGLISH), "")
        this.javaType.set(orgJavaType)
        this.defaultJavaType = orgJavaType
        val impJavaType = JdbcJavaTypeRegistrar.javaType(orgJavaType, "")
        this.setImportJavaType(impJavaType)
        this.defaultImportJavaType = impJavaType
        val propName = transformProperty(originalColumnName)
        defaultPropertyName = propName
        this.propertyName.set(propName)
        this.setComment(originalComment)
        this.defaultComment = originalComment
        this.commentOverride = originalComment
        // 监听值变化处理非必填项，使用旧的值(propertyName, javaType, importJavaType)
        javaType.addListener { _, oldValue, newValue ->
            if (isNotEmpty(newValue)) {
                // 判断数据类型(基本数据类型、非基本数据类型)
                if (JdbcJavaTypeRegistrar.notNeedImport(newValue)) {
                    setImportJavaType("")
                } else {
                    val realImportJavaType = JdbcJavaTypeRegistrar.javaType(newValue, "")
                    if (isNotEmpty(realImportJavaType)) {
                        setImportJavaType(realImportJavaType)
                    }
                }
            } else {
                setJavaType(oldValue)
            }
        }
        propertyName.addListener { _, oldValue, newValue ->
            if (!isNotEmpty(newValue)) {
                setPropertyName(oldValue)
            }
        }
        importJavaType.addListener { _, oldValue, newValue ->
            if (isNotEmpty(newValue)) {
                // 对比名称是否一致
                val array = newValue.split(".")
                if (array[array.size - 1] != getJavaType()) {
                    setImportJavaType(oldValue)
                }
            } else {
                val need = JdbcJavaTypeRegistrar.javaType(getJavaType(), "")
                if (isNotEmpty(need)) {
                    setImportJavaType(oldValue)
                }
            }
        }
    }

    /**
     * 确认修改
     */
    fun confirmChange() {
        this.checkedOverride = getChecked().toString()
        this.propertyNameOverride = getPropertyName()
        this.javaTypeOverride = getJavaType()
        this.importJavaTypeOverride = getImportJavaType()
        this.typeHandleOverride = getTypeHandle()
        this.commentOverride = getComment()
    }

    /**
     * 取消修改(回滚)
     */
    fun rollback() {
        this.checkedRollback()
        this.javaTypeRollback()
        this.propertyNameRollback()
        this.importJavaTypeRollback()
        this.typeHandleRollback()
        this.commentRollback()
    }

    private fun checkedRollback() {
        this.checked.set(getCheckedValue())
    }

    private fun propertyNameRollback() {
        this.propertyName.set(getPropertyNameValue())
    }

    private fun javaTypeRollback() {
        this.javaType.set(getJavaTypeValue())
    }

    private fun importJavaTypeRollback() {
        this.importJavaType.set(getImportJavaTypeValue())
    }

    private fun typeHandleRollback() {
        this.typeHandle.set(getTypeHandleValue())
    }
    
    private fun commentRollback() {
        this.comment.set(getCommentValue())
    }

    private fun getCheckedValue(): Boolean {
        return this.checkedOverride.takeIf {
            it.isEmpty()
        }?.run {
            defaultChecked
        } ?: run {
            checkedOverride.toBoolean()
        }
    }

    private fun getPropertyNameValue(): String {
        return this.propertyNameOverride.takeIf {
            it.isBlank()
        }?.run {
            defaultPropertyName
        } ?: run {
            propertyNameOverride
        }
    }

    private fun getJavaTypeValue(): String {
        return this.javaTypeOverride.takeIf {
            it.isBlank()
        }?.run {
            defaultJavaType
        } ?: run {
            this.javaTypeOverride
        }
    }

    private fun getImportJavaTypeValue(): String {
        return this.importJavaTypeOverride.takeIf {
            it.isBlank()
        }?.run {
            defaultImportJavaType
        } ?: run {
            importJavaTypeOverride
        }
    }

    private fun getTypeHandleValue(): String {
        return this.typeHandleOverride.takeIf {
            it.isBlank()
        }?.run {
            defaultTypeHandle
        } ?: run {
            typeHandleOverride
        }
    }
    
    private fun getCommentValue(): String {
        return this.commentOverride.takeIf { 
            it.isBlank()
        } ?.run {
            defaultComment
        } ?: run {
            commentOverride
        }
    }

    fun getDefaultPropertyName(): String {
        return this.defaultPropertyName
    }

    fun getDefaultChecked(): Boolean {
        return this.defaultChecked
    }

    fun getDefaultJavaType(): String {
        return this.defaultJavaType
    }

    fun getDefaultImportJavaType(): String {
        return this.defaultImportJavaType
    }

    fun getDefaultTypeHandle(): String {
        return this.defaultTypeHandle
    }
    
    fun getDefaultComment(): String {
        return this.defaultComment
    }
    

    fun getChecked(): Boolean {
        return this.checked.get()
    }

    fun setChecked(checked: Boolean) {
        this.checked.set(checked)
    }

    fun checkedProperty(): BooleanProperty {
        return this.checked
    }

    fun getColumnName(): String {
        return this.columnName.get()
    }

    fun setColumnName(columnName: String) {
        this.columnName.set(columnName)
    }

    fun columnNameProperty(): StringProperty {
        return this.columnName
    }

    fun getColumnLength(): String {
        return this.columnLength.get()
    }

    fun setColumnLength(columnLength: String) {
        this.columnLength.set(columnLength)
    }

    fun getJdbcType(): String {
        return this.jdbcType.get()
    }

    fun setJdbcType(jdbcType: String) {
        this.jdbcType.set(jdbcType)
    }

    fun jdbcTypeProperty(): StringProperty {
        return this.jdbcType
    }

    fun getJavaType(): String {
        return this.javaType.get()
    }

    fun setJavaType(javaType: String) {
        this.javaType.set(javaType)
    }

    fun javaTypeProperty(): StringProperty {
        return this.javaType
    }

    fun getPropertyName(): String {
        return this.propertyName.get()
    }

    fun setPropertyName(propertyName: String) {
        this.propertyName.set(propertyName)
    }

    fun propertyNameProperty(): StringProperty {
        return this.propertyName
    }

    fun getComment(): String {
        return this.comment.get()
    }

    fun setComment(comment: String) {
        this.comment.set(comment)
    }

    fun commentProperty(): StringProperty {
        return this.comment
    }

    fun isPrimary(): Boolean {
        return this.primary.get()
    }

    fun setPrimary(primary: Boolean) {
        this.primary.set(primary)
    }

    fun getIndex(): String {
        return this.index.get()
    }

    fun setIndex(index: String) {
        this.index.set(index)
    }

    fun getImportJavaType(): String {
        return this.importJavaType.get()
    }

    fun setImportJavaType(importJavaType: String) {
        this.importJavaType.set(importJavaType)
    }

    fun importJavaTypeProperty(): StringProperty {
        return this.importJavaType
    }

    fun getTypeHandle(): String {
        return this.typeHandle.get()
    }

    fun setTypeHandle(typeHandle: String) {
        this.typeHandle.set(typeHandle)
    }

    fun typeHandleProperty(): StringProperty {
        return this.typeHandle
    }

    fun clearOverrides() {
        this.checkedOverride = ""
        this.propertyNameOverride = ""
        this.javaTypeOverride = ""
        this.importJavaTypeOverride = ""
        this.typeHandleOverride = ""
    }


    fun applyOverrides() {
        this.setChecked(this.checkedOverride.toBoolean())
        if (isNotEmpty(this.propertyNameOverride)) {
            this.setPropertyName(this.propertyNameOverride)
        }
        if (isNotEmpty(this.javaTypeOverride)) {
            this.setJavaType(this.javaTypeOverride)
        }
        if (isNotEmpty(this.importJavaTypeOverride)) {
            this.setImportJavaType(this.importJavaTypeOverride)
        }
        if (isNotEmpty(this.typeHandleOverride)) {
            this.setTypeHandle(this.typeHandleOverride)
        }
        this.clearOverrides()
    }

    private fun isNotEmpty(value: String?): Boolean {
        return !value.isNullOrBlank()
    }

    private fun startWithIsPrefix(value: String): Boolean {
        return value.toUpperCase(Locale.ENGLISH).startsWith("IS_")
    }

    private fun transformProperty(columnName: String): String {
        return columnName.takeIf {
            startWithIsPrefix(it)
        }?.run {
            val newColumnName = columnName.substring(3).toUpperCase(Locale.ENGLISH)
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, newColumnName)
        } ?: run {
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName)
        }
    }
}
