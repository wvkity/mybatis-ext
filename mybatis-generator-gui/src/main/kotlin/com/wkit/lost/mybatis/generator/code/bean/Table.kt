package com.wkit.lost.mybatis.generator.code.bean

data class Table(var className: String, var tableName: String, var author: String, var comment: String) {

    var packageName = ""
    var entityImplSerializable: Boolean = true
    var dtoEntityImplSerializable: Boolean = true
    var hasPrimaryKey = false
    var schema = ""
    var columns = ArrayList<Column>()
    var importJavaTypes = HashSet<String>()
    var importAnnotationJavaTypes = HashSet<String>()

    fun addImportJavaType(importJavaType: String?) {
        importJavaType.takeIf {
            !it.isNullOrBlank()
        }?.run {
            importJavaTypes.add("import $this;")
        }
    }

    fun addAnnotationJavaType(annotationType: String?) {
        annotationType.takeIf {
            !it.isNullOrBlank()
        }?.run {
            importAnnotationJavaTypes.add("import $this;")
        }
    }
}