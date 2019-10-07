package com.wkit.lost.mybatis.starter.example.config;

import com.wkit.lost.mybatis.filling.MetaObjectFillingDependency;

public class FillingDependency implements MetaObjectFillingDependency {

    @Override
    public String currentUserName() {
        return "张三";
    }

    @Override
    public Object currentUserId() {
        return "";
    }
}
