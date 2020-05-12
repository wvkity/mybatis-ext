package com.wkit.lost.mybatis.generator.code.bean

data class Column(var columnName: String,
                  var propertyName: String,
                  var defaultPropertyName: String,
                  var jdbcType: String,
                  var javaType: String,
                  var importJavaType: String,
                  var primary: Boolean,
                  var comment: String,
                  var typeHandler: String) {
    var primaryKey: String = "UNKNOWN"
    var typeHandlerClass = ""
}