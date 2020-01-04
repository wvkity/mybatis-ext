package com.wkit.lost.mybatis.generator.bean;

import com.wkit.lost.mybatis.generator.mapping.JdbcJavaTypeRegistrar
import com.wkit.lost.mybatis.generator.utils.DatabaseUtil
import com.wvkit.lost.mybatis.generator.utils.CaseFormat
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import java.util.*

class Column constructor(var originalColumnName: String, var originalJdbcType: String) {
    
    /**
     * 是否选择
     */
    var checked = SimpleBooleanProperty(true)
    /**
     * 列名
     */
    var columnName = SimpleStringProperty("")
    /**
     * 长度
     */
    var columnLength = SimpleStringProperty("")
    /**
     * JDBC类型
     */
    var jdbcType = SimpleStringProperty("")
    /**
     * Java类型
     */
    var javaType = SimpleStringProperty("")
    /**
     * 属性名
     */
    var propertyName = SimpleStringProperty("")
    /**
     * 注释
     */
    var comment = SimpleStringProperty("")
    /**
     * 是否主键
     */
    var primary = SimpleBooleanProperty(false)
    /**
     * 列索引
     */
    var index = SimpleStringProperty("")
    /**
     * 导入的Java类型
     */
    var importJavaType = SimpleStringProperty("")
    /**
     * 处理器
     */
    var typeHandle = SimpleStringProperty("")
    
    //// 默认属性 ////
    private val defaultChecked = true
    private val defaultPropertyName: String
    private val defaultJavaType: String
    private val defaultImportJavaType: String
    private val defaultTypeHandle: String = ""
    ///////////////
    
    init {
        this.columnName.set(originalColumnName)
        this.jdbcType.set(originalJdbcType)
        val orgJavaType = JdbcJavaTypeRegistrar.javaTypeString(originalJdbcType.toLowerCase(Locale.ENGLISH), "")
        this.javaType.set(orgJavaType)
        this.defaultJavaType = orgJavaType
        val impJavaType =JdbcJavaTypeRegistrar.javaType(orgJavaType, "")
        this.setImportJavaType(impJavaType)
        this.defaultImportJavaType = impJavaType
        val propName = transformProperty(originalColumnName)
        defaultPropertyName = propName
        this.propertyName.set(propName)
    }

    /////// overrides ///////
    var checkedOverride = false
    var javaTypeOverride = ""
    var propertyNameOverride = ""
    var importJavaTypeOverride = ""
    var typeHandleOverride = ""
    ////////////////////////
    
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
        this.checkedOverride = false
        this.propertyNameOverride = ""
        this.javaTypeOverride = ""
        this.importJavaTypeOverride = ""
        this.typeHandleOverride = ""
    }
    
    fun applyOverrides() {
        if (this.checkedOverride) {
            this.setChecked(this.checkedOverride)
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
    }
    
    fun isNotEmpty(value: String?): Boolean {
        return !value.isNullOrBlank()
    }

    private fun startWithIsPrefix(value: String): Boolean {
        return value.toUpperCase(Locale.ENGLISH).startsWith("IS_")
    }

    private fun transformProperty(columnName: String): String {
        return columnName.takeIf {
            startWithIsPrefix(it)
        } ?.run {
            val newColumnName = columnName.substring(3).toUpperCase(Locale.ENGLISH)
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, newColumnName)
        } ?: run {
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName)
        }
    }
}
