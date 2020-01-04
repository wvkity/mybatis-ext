package com.wkit.lost.mybatis.generator.bean

import com.wvkit.lost.mybatis.generator.utils.CaseFormat
import javafx.beans.property.SimpleStringProperty

class Table constructor(name: String) {
    /**
     * 表名(禁止修改)
     */
    val name = SimpleStringProperty("")
    /**
     * 表名前缀
     */
    var prefix = SimpleStringProperty("")
    /**
     * 默认类名(禁止修改)
     */
    var defaultClassName = SimpleStringProperty("")
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
    
    init {
        this.name.set(name)
        this.defaultClassName.set(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name))
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
}