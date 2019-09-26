package com.wkit.lost.mybatis.starter.example.config;

import com.wkit.lost.mybatis.filling.MetaObjectFillingDependency;

public class FillingDependency implements MetaObjectFillingDependency {
    @Override
    public String currentUserName() {
        return "我就是测试的";
    }

    @Override
    public Object currentUserId() {
        return "";
    }
}
