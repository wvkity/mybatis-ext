package com.wkit.lost.mybatis.generator.bean;

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

class Column {
    var columnName = SimpleStringProperty()
    var columnLength = SimpleStringProperty()
    var jdbcType = SimpleStringProperty()
    var javaType = SimpleStringProperty()
    var propertyName = SimpleStringProperty()
    var comment = SimpleStringProperty()
    var primary = SimpleBooleanProperty(false)
    var index = SimpleStringProperty()
    
    fun getColumnName(): String {
        return this.columnName.get()
    }
    
    fun setColumnName(columnName: String) {
        this.columnName.set(columnName)
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
    
    fun getJavaType(): String {
        return this.javaType.get()
    }
    
    fun setJavaType(javaType: String) {
        this.javaType.set(javaType)
    }
    
    fun getPropertyName(): String {
        return this.propertyName.get()
    }
    
    fun setPropertyName(propertyName: String) {
        this.propertyName.set(propertyName)
    }
    
    fun getComment(): String {
        return this.comment.get()
    }
    
    fun setComment(comment: String) {
        this.comment.set(comment)
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
}
