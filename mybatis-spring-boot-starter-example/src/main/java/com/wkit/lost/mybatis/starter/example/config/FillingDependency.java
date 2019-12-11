package com.wkit.lost.mybatis.starter.example.config;

import com.wkit.lost.mybatis.filling.MetaObjectFillAuxiliary;

public class FillingDependency implements MetaObjectFillAuxiliary {

    @Override
    public String operator() {
        return "张三";
    }

    @Override
    public Object operationUserId() {
        return "";
    }
}
